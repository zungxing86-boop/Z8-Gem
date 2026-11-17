package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_mastery")
data class WordMasteryEntity(
    @PrimaryKey val wordId: String,
    val lessonId: Int,
    val masteryLevel: Int = 0, // 0..5 (5 = fully mastered)
    val correctCount: Int = 0,
    val wrongCount: Int = 0,
    val lastReviewedAt: Long = 0L,
    val nextReviewAt: Long = 0L
)

@Entity(tableName = "lesson_progress")
data class LessonProgressEntity(
    @PrimaryKey val lessonId: Int,
    val conversationDone: Boolean = false,
    val vocabDone: Boolean = false,
    val grammarDone: Boolean = false,
    val exercisesDone: Boolean = false,
    val quizBestScore: Int = 0,
    val quizTotalQuestions: Int = 0,
    val quizAttempts: Int = 0,
    val lastUpdatedAt: Long = 0L
) {
    val completionPercent: Int
        get() {
            var done = 0
            if (conversationDone) done++
            if (vocabDone) done++
            if (grammarDone) done++
            if (exercisesDone) done++
            if (quizAttempts > 0 && quizTotalQuestions > 0 && quizBestScore * 100 / quizTotalQuestions >= 60) {
                done++
            }
            return (done * 100) / 5
        }
}

@Entity(tableName = "exercise_attempts")
data class ExerciseAttemptEntity(
    @PrimaryKey val exerciseId: String,
    val lessonId: Int,
    val solved: Boolean = false,
    val attemptsUsed: Int = 0,
    val hintsUsed: Int = 0,
    val updatedAt: Long = 0L
)

@Entity(tableName = "mistake_logs")
data class MistakeLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val lessonId: Int,
    val topicTag: String,
    val itemTitle: String,
    val userAnswer: String,
    val correctAnswer: String,
    val timestamp: Long = System.currentTimeMillis()
)
