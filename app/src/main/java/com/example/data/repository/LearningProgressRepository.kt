package com.example.data.repository

import com.example.data.curriculum.Prospect2Catalog
import com.example.data.local.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class LearningProgressRepository(
    private val dao: ProspectDao,
    private val prefsStore: UserPrefsStore
) {
    val wordMasteries: Flow<List<WordMasteryEntity>> = dao.observeAllWordMastery()
    val lessonProgresses: Flow<List<LessonProgressEntity>> = dao.observeAllLessonProgress()
    val exerciseAttempts: Flow<List<ExerciseAttemptEntity>> = dao.observeAllExerciseAttempts()
    val recentMistakes: Flow<List<MistakeLogEntity>> = dao.observeRecentMistakes()
    val preferences: Flow<StudentPreferences> = prefsStore.preferencesFlow

    suspend fun saveLastLocation(unitId: Int, stageIndex: Int) {
        prefsStore.saveLastLocation(unitId, stageIndex)
    }

    suspend fun setSpeechRate(rate: Float) = prefsStore.setSpeechRate(rate)

    suspend fun setShowPersianTranslation(show: Boolean) = prefsStore.setShowPersianTranslation(show)

    suspend fun setStudentName(name: String) = prefsStore.setStudentName(name)

    suspend fun markStageDone(unitId: Int, stageIndex: Int) {
        val existing = dao.getLessonProgress(unitId) ?: LessonProgressEntity(lessonId = unitId)
        val updated = when (stageIndex) {
            0 -> existing.copy(conversationDone = true)
            1 -> existing.copy(vocabDone = true)
            2 -> existing.copy(grammarDone = true)
            3 -> existing.copy(exercisesDone = true)
            else -> existing
        }.copy(lastUpdatedAt = System.currentTimeMillis())
        dao.upsertLessonProgress(updated)
        prefsStore.addXp(15)
    }

    suspend fun recordWordReview(wordId: String, lessonId: Int, isCorrect: Boolean) {
        val now = System.currentTimeMillis()
        val existing = dao.getWordMastery(wordId) ?: WordMasteryEntity(wordId = wordId, lessonId = lessonId)
        val newLevel = if (isCorrect) {
            (existing.masteryLevel + 1).coerceAtMost(5)
        } else {
            (existing.masteryLevel - 1).coerceAtLeast(0)
        }
        // Simple spaced-repetition interval: wrong = immediate (1 min), higher level = longer
        val intervalMs = when {
            !isCorrect -> 60_000L
            newLevel == 1 -> 10 * 60_000L
            newLevel == 2 -> 60 * 60_000L
            newLevel == 3 -> 12 * 3600_000L
            newLevel == 4 -> 2 * 24 * 3600_000L
            else -> 5 * 24 * 3600_000L
        }
        val updated = existing.copy(
            masteryLevel = newLevel,
            correctCount = existing.correctCount + if (isCorrect) 1 else 0,
            wrongCount = existing.wrongCount + if (!isCorrect) 1 else 0,
            lastReviewedAt = now,
            nextReviewAt = now + intervalMs
        )
        dao.upsertWordMastery(updated)
        if (isCorrect) {
            prefsStore.addXp(10)
        } else {
            val word = Prospect2Catalog.allWords.firstOrNull { it.id == wordId }
            if (word != null) {
                dao.insertMistake(
                    MistakeLogEntity(
                        lessonId = lessonId,
                        topicTag = "واژگان",
                        itemTitle = word.english,
                        userAnswer = "نیاز به مرور",
                        correctAnswer = word.persian
                    )
                )
            }
        }
    }

    suspend fun recordExerciseResult(
        exerciseId: String,
        lessonId: Int,
        solved: Boolean,
        hintsUsed: Int,
        userAnswer: String,
        correctAnswer: String,
        titleFa: String
    ) {
        val existing = dao.getExerciseAttempt(exerciseId)
            ?: ExerciseAttemptEntity(exerciseId = exerciseId, lessonId = lessonId)
        val updated = existing.copy(
            solved = existing.solved || solved,
            attemptsUsed = existing.attemptsUsed + 1,
            hintsUsed = maxOf(existing.hintsUsed, hintsUsed),
            updatedAt = System.currentTimeMillis()
        )
        dao.upsertExerciseAttempt(updated)
        if (solved) {
            prefsStore.addXp(25)
        } else {
            dao.insertMistake(
                MistakeLogEntity(
                    lessonId = lessonId,
                    topicTag = "تمرین کتاب",
                    itemTitle = titleFa,
                    userAnswer = userAnswer,
                    correctAnswer = correctAnswer
                )
            )
        }
    }

    suspend fun recordQuizCompletion(
        unitId: Int,
        score: Int,
        total: Int,
        mistakes: List<Triple<String, String, String>> // topicTag, prompt, correctAnswer
    ) {
        val existing = dao.getLessonProgress(unitId) ?: LessonProgressEntity(lessonId = unitId)
        val updated = existing.copy(
            quizBestScore = maxOf(existing.quizBestScore, score),
            quizTotalQuestions = total,
            quizAttempts = existing.quizAttempts + 1,
            lastUpdatedAt = System.currentTimeMillis()
        )
        dao.upsertLessonProgress(updated)
        prefsStore.addXp(score * 20)
        mistakes.forEach { (tag, prompt, correct) ->
            dao.insertMistake(
                MistakeLogEntity(
                    lessonId = unitId,
                    topicTag = tag,
                    itemTitle = prompt,
                    userAnswer = "پاسخ نادرست در آزمون",
                    correctAnswer = correct
                )
            )
        }
    }

    suspend fun recordGrammarMistake(lessonId: Int, topicTitle: String, userAnswer: String, correct: String) {
        dao.insertMistake(
            MistakeLogEntity(
                lessonId = lessonId,
                topicTag = "گرامر",
                itemTitle = topicTitle,
                userAnswer = userAnswer,
                correctAnswer = correct
            )
        )
    }

    suspend fun clearAllProgress() {
        dao.clearWordMastery()
        dao.clearLessonProgress()
        dao.clearExerciseAttempts()
        dao.clearAllMistakes()
        prefsStore.resetXpAndLocation()
    }

    suspend fun buildWeaknessSummary(): String {
        val words = dao.observeAllWordMastery().first()
        val mistakes = dao.observeRecentMistakes().first()
        val weakWordIds = words.filter { it.wrongCount > it.correctCount || (it.wrongCount > 0 && it.masteryLevel < 3) }
            .map { it.wordId }.toSet()
        val weakWords = Prospect2Catalog.allWords.filter { it.id in weakWordIds }.take(8).map { "${it.english} (${it.persian})" }
        val weakTopics = mistakes.take(6).map { "${it.topicTag}: ${it.itemTitle}" }.distinct()
        return buildString {
            if (weakWords.isNotEmpty()) {
                append("لغات نیازمند مرور: ${weakWords.joinToString("، ")}. ")
            }
            if (weakTopics.isNotEmpty()) {
                append("مباحثی که اخیراً اشتباه داشته‌ام: ${weakTopics.joinToString(" | ")}.")
            }
        }
    }
}
