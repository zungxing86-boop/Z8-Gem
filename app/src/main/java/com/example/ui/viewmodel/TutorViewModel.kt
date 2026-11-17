package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.SmartTutorRepository
import com.example.ai.TutorStreamState
import com.example.audio.PlaybackState
import com.example.audio.PronunciationResult
import com.example.audio.SpeechTutorEngine
import com.example.data.curriculum.Prospect2Catalog
import com.example.data.local.*
import com.example.data.model.ConversationLine
import com.example.data.model.CurriculumUnit
import com.example.data.model.VocabWord
import com.example.data.repository.LearningProgressRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ChatMessage(
    val id: Long = System.nanoTime(),
    val isStudent: Boolean,
    val text: String,
    val isOfflineFallback: Boolean = false,
    val isError: Boolean = false
)

data class TutorDashboardState(
    val preferences: StudentPreferences = StudentPreferences(),
    val lessonProgressMap: Map<Int, LessonProgressEntity> = emptyMap(),
    val wordMasteryMap: Map<String, WordMasteryEntity> = emptyMap(),
    val exerciseAttemptMap: Map<String, ExerciseAttemptEntity> = emptyMap(),
    val recentMistakes: List<MistakeLogEntity> = emptyList(),
    val overallBookPercent: Int = 0,
    val masteredWordsCount: Int = 0,
    val weakWords: List<VocabWord> = emptyList()
)

class TutorViewModel(application: Application) : AndroidViewModel(application) {

    private val database = ProspectDatabase.getInstance(application)
    private val prefsStore = UserPrefsStore(application)
    private val repository = LearningProgressRepository(database.prospectDao(), prefsStore)
    private val speechEngine = SpeechTutorEngine(application)
    private val smartTutor = SmartTutorRepository()

    private var activeTutorJob: Job? = null

    val playbackState: StateFlow<PlaybackState> = speechEngine.playbackState
    val pronunciationResult: StateFlow<PronunciationResult> = speechEngine.pronunciationResult

    private val _chatMessages = MutableStateFlow(
        listOf(
            ChatMessage(
                isStudent = false,
                text = "سلام! من معلم هوشمند کتاب انگلیسی پایه هشتم (Prospect 2) هستم. می‌توانی از من بخواهی گرامر را ساده‌تر توضیح دهم، مثال‌های جدید خارج از کتاب بسازم، یا نقاط ضعف تو را با هم تمرین کنیم!",
                isOfflineFallback = true
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _tutorStreamState = MutableStateFlow<TutorStreamState>(TutorStreamState.Idle)
    val tutorStreamState: StateFlow<TutorStreamState> = _tutorStreamState.asStateFlow()

    val dashboardState: StateFlow<TutorDashboardState> = combine(
        repository.preferences,
        repository.lessonProgresses,
        repository.wordMasteries,
        repository.exerciseAttempts,
        repository.recentMistakes
    ) { prefs, lessons, words, exercises, mistakes ->
        val lessonMap = lessons.associateBy { it.lessonId }
        val wordMap = words.associateBy { it.wordId }
        val exerciseMap = exercises.associateBy { it.exerciseId }

        val totalUnits = Prospect2Catalog.units.size
        val sumPercent = Prospect2Catalog.units.sumOf { unit ->
            lessonMap[unit.id]?.completionPercent ?: 0
        }
        val overallPercent = if (totalUnits > 0) sumPercent / totalUnits else 0
        val masteredCount = words.count { it.masteryLevel >= 3 }

        val weakIds = words.filter {
            it.wrongCount > 0 && (it.masteryLevel < 3 || it.wrongCount >= it.correctCount)
        }.map { it.wordId }.toSet()
        val weakWordObjects = Prospect2Catalog.allWords.filter { it.id in weakIds }

        TutorDashboardState(
            preferences = prefs,
            lessonProgressMap = lessonMap,
            wordMasteryMap = wordMap,
            exerciseAttemptMap = exerciseMap,
            recentMistakes = mistakes,
            overallBookPercent = overallPercent,
            masteredWordsCount = masteredCount,
            weakWords = weakWordObjects
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TutorDashboardState()
    )

    init {
        viewModelScope.launch {
            repository.preferences.collect { prefs ->
                speechEngine.setSpeechRate(prefs.speechRate)
            }
        }
    }

    // ---- Speech & Pronunciation ----

    fun speakText(text: String, tag: String = text, lineIndex: Int = -1) {
        speechEngine.speakText(text, tag, lineIndex)
    }

    fun playConversation(lines: List<ConversationLine>, startIndex: Int = 0) {
        speechEngine.playConversation(lines, startIndex)
    }

    fun stopSpeaking() {
        speechEngine.stopSpeaking()
    }

    fun setSpeechRate(rate: Float) {
        speechEngine.setSpeechRate(rate)
        viewModelScope.launch {
            repository.setSpeechRate(rate)
        }
    }

    fun setShowPersianTranslation(show: Boolean) {
        viewModelScope.launch {
            repository.setShowPersianTranslation(show)
        }
    }

    fun setStudentName(name: String) {
        viewModelScope.launch {
            repository.setStudentName(name)
        }
    }

    fun isSpeechRecognitionAvailable(): Boolean = speechEngine.isSpeechRecognitionAvailable()

    fun startPronunciationPractice(targetEnglish: String) {
        speechEngine.startPronunciationPractice(targetEnglish)
    }

    fun clearPronunciationResult() {
        speechEngine.clearPronunciationResult()
    }

    // ---- Progress & Spaced Repetition ----

    fun saveLastLocation(unitId: Int, stageIndex: Int) {
        viewModelScope.launch {
            repository.saveLastLocation(unitId, stageIndex)
        }
    }

    fun markStageDone(unitId: Int, stageIndex: Int) {
        viewModelScope.launch {
            repository.markStageDone(unitId, stageIndex)
        }
    }

    fun recordWordReview(word: VocabWord, isCorrect: Boolean) {
        viewModelScope.launch {
            repository.recordWordReview(word.id, word.lessonId, isCorrect)
        }
    }

    fun recordGrammarMistake(lessonId: Int, topicTitle: String, userAnswer: String, correct: String) {
        viewModelScope.launch {
            repository.recordGrammarMistake(lessonId, topicTitle, userAnswer, correct)
        }
    }

    fun recordExerciseResult(
        exerciseId: String,
        lessonId: Int,
        solved: Boolean,
        hintsUsed: Int,
        userAnswer: String,
        correctAnswer: String,
        titleFa: String
    ) {
        viewModelScope.launch {
            repository.recordExerciseResult(
                exerciseId = exerciseId,
                lessonId = lessonId,
                solved = solved,
                hintsUsed = hintsUsed,
                userAnswer = userAnswer,
                correctAnswer = correctAnswer,
                titleFa = titleFa
            )
        }
    }

    fun recordQuizCompletion(
        unitId: Int,
        score: Int,
        total: Int,
        mistakes: List<Triple<String, String, String>>
    ) {
        viewModelScope.launch {
            repository.recordQuizCompletion(unitId, score, total, mistakes)
        }
    }

    fun clearAllProgress() {
        viewModelScope.launch {
            repository.clearAllProgress()
        }
    }

    fun buildSmartReviewQueue(lessonFilter: Int? = null): List<VocabWord> {
        val state = dashboardState.value
        val now = System.currentTimeMillis()
        val pool = if (lessonFilter == null) {
            Prospect2Catalog.allWords
        } else {
            Prospect2Catalog.wordsForUnit(lessonFilter)
        }
        return pool.sortedWith(compareBy<VocabWord> { word ->
            val mastery = state.wordMasteryMap[word.id]
            when {
                mastery == null -> 1 // Unseen words second priority
                mastery.wrongCount > mastery.correctCount -> 0 // Forgotten/struggling words FIRST!
                mastery.nextReviewAt <= now && mastery.masteryLevel < 5 -> 0
                else -> 2 + mastery.masteryLevel
            }
        }.thenByDescending { word ->
            state.wordMasteryMap[word.id]?.wrongCount ?: 0
        })
    }

    // ---- Smart AI Tutor ----

    fun askTutor(
        questionFa: String,
        unit: CurriculumUnit?,
        forceOffline: Boolean = false
    ) {
        val trimmed = questionFa.trim()
        if (trimmed.isEmpty()) return

        activeTutorJob?.cancel()
        _chatMessages.value = _chatMessages.value + ChatMessage(isStudent = true, text = trimmed)

        activeTutorJob = viewModelScope.launch {
            val weaknessSummary = repository.buildWeaknessSummary()
            smartTutor.askTutorStream(trimmed, unit, weaknessSummary, forceOfflineMode = forceOffline)
                .collect { state ->
                    _tutorStreamState.value = state
                    when (state) {
                        is TutorStreamState.Success -> {
                            _chatMessages.value = _chatMessages.value + ChatMessage(
                                isStudent = false,
                                text = state.text,
                                isOfflineFallback = state.isOfflineFallback
                            )
                            _tutorStreamState.value = TutorStreamState.Idle
                        }
                        is TutorStreamState.Error -> {
                            _chatMessages.value = _chatMessages.value + ChatMessage(
                                isStudent = false,
                                text = state.message,
                                isError = true
                            )
                        }
                        else -> Unit
                    }
                }
        }
    }

    fun clearChat() {
        activeTutorJob?.cancel()
        _tutorStreamState.value = TutorStreamState.Idle
        _chatMessages.value = listOf(
            ChatMessage(
                isStudent = false,
                text = "گفتگو پاک شد! هر سؤالی درباره درس‌های ۱ تا ۷ کتاب Prospect 2 داری بپرس.",
                isOfflineFallback = true
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        speechEngine.release()
    }
}
