package com.example.audio

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.example.data.model.ConversationLine
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

private const val TAG = "SpeechTutorEngine"

data class PlaybackState(
    val isReady: Boolean = false,
    val isPlaying: Boolean = false,
    val activeUtteranceTag: String? = null,
    val activeLineIndex: Int = -1,
    val activeWordStart: Int = -1,
    val activeWordEnd: Int = -1,
    val speechRate: Float = 0.9f,
    val ttsUnavailableMessage: String? = null
)

data class PronunciationResult(
    val isListening: Boolean = false,
    val recognizedText: String = "",
    val scorePercent: Int? = null,
    val feedbackFa: String = "",
    val errorMessage: String? = null
)

class SpeechTutorEngine(private val context: Context) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var tts: TextToSpeech? = null
    private var speechRecognizer: SpeechRecognizer? = null
    private var fallbackHighlightJob: Job? = null
    private var sequenceJob: Job? = null

    private val _playbackState = MutableStateFlow(PlaybackState())
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    private val _pronunciationResult = MutableStateFlow(PronunciationResult())
    val pronunciationResult: StateFlow<PronunciationResult> = _pronunciationResult.asStateFlow()

    @Volatile
    private var receivedRangeStartForCurrentUtterance = false

    private var currentUtteranceCompletedCallback: (() -> Unit)? = null

    init {
        initTts()
    }

    private fun initTts() {
        try {
            tts = TextToSpeech(context.applicationContext) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val engine = tts
                    val result = engine?.setLanguage(Locale.US)
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        val ukResult = engine?.setLanguage(Locale.UK)
                        if (ukResult == TextToSpeech.LANG_MISSING_DATA || ukResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                            _playbackState.value = _playbackState.value.copy(
                                isReady = true,
                                ttsUnavailableMessage = "بسته صوتی انگلیسی آفلاین روی دستگاه کامل نیست؛ در صورت نیاز از تنظیمات متن‌به‌گفتار (TTS) سیستم زبان انگلیسی را فعال کنید."
                            )
                        } else {
                            _playbackState.value = _playbackState.value.copy(isReady = true, ttsUnavailableMessage = null)
                        }
                    } else {
                        _playbackState.value = _playbackState.value.copy(isReady = true, ttsUnavailableMessage = null)
                    }
                    engine?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                        override fun onStart(utteranceId: String?) {
                            scope.launch {
                                _playbackState.value = _playbackState.value.copy(
                                    isPlaying = true,
                                    activeUtteranceTag = utteranceId
                                )
                            }
                        }

                        override fun onRangeStart(utteranceId: String?, start: Int, end: Int, frame: Int) {
                            receivedRangeStartForCurrentUtterance = true
                            fallbackHighlightJob?.cancel()
                            scope.launch {
                                _playbackState.value = _playbackState.value.copy(
                                    activeWordStart = start,
                                    activeWordEnd = end
                                )
                            }
                        }

                        override fun onDone(utteranceId: String?) {
                            fallbackHighlightJob?.cancel()
                            scope.launch {
                                val callback = currentUtteranceCompletedCallback
                                currentUtteranceCompletedCallback = null
                                if (callback != null) {
                                    callback()
                                } else {
                                    _playbackState.value = _playbackState.value.copy(
                                        isPlaying = false,
                                        activeUtteranceTag = null,
                                        activeWordStart = -1,
                                        activeWordEnd = -1
                                    )
                                }
                            }
                        }

                        @Deprecated("Deprecated in Java")
                        override fun onError(utteranceId: String?) {
                            fallbackHighlightJob?.cancel()
                            scope.launch {
                                currentUtteranceCompletedCallback = null
                                _playbackState.value = _playbackState.value.copy(
                                    isPlaying = false,
                                    activeUtteranceTag = null,
                                    activeWordStart = -1,
                                    activeWordEnd = -1
                                )
                            }
                        }
                    })
                } else {
                    Log.w(TAG, "TextToSpeech init failed with status $status")
                    _playbackState.value = _playbackState.value.copy(
                        isReady = false,
                        ttsUnavailableMessage = "موتور تلفظ صوتی دستگاه در دسترس نیست."
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing TextToSpeech", e)
        }
    }

    fun setSpeechRate(rate: Float) {
        val clamped = rate.coerceIn(0.6f, 1.4f)
        tts?.setSpeechRate(clamped)
        _playbackState.value = _playbackState.value.copy(speechRate = clamped)
    }

    fun speakText(text: String, tag: String = text, lineIndex: Int = -1) {
        sequenceJob?.cancel()
        speakSingleInternal(text, tag, lineIndex, onDone = null)
    }

    fun playConversation(lines: List<ConversationLine>, startIndex: Int = 0) {
        sequenceJob?.cancel()
        if (lines.isEmpty()) return
        sequenceJob = scope.launch {
            for (index in startIndex until lines.size) {
                if (!isActive) break
                val line = lines[index]
                val deferred = CompletableDeferred<Unit>()
                speakSingleInternal(
                    text = line.english,
                    tag = "conv_line_$index",
                    lineIndex = index,
                    onDone = { deferred.complete(Unit) }
                )
                deferred.await()
                delay(350L)
            }
            _playbackState.value = _playbackState.value.copy(
                isPlaying = false,
                activeUtteranceTag = null,
                activeLineIndex = -1,
                activeWordStart = -1,
                activeWordEnd = -1
            )
        }
    }

    private fun speakSingleInternal(
        text: String,
        tag: String,
        lineIndex: Int,
        onDone: (() -> Unit)?
    ) {
        fallbackHighlightJob?.cancel()
        currentUtteranceCompletedCallback = onDone
        receivedRangeStartForCurrentUtterance = false

        val cleanText = text.trim()
        if (cleanText.isEmpty()) {
            onDone?.invoke()
            return
        }

        val rate = _playbackState.value.speechRate
        tts?.setSpeechRate(rate)
        _playbackState.value = _playbackState.value.copy(
            isPlaying = true,
            activeUtteranceTag = tag,
            activeLineIndex = lineIndex,
            activeWordStart = 0,
            activeWordEnd = cleanText.indexOf(' ').let { if (it == -1) cleanText.length else it }
        )

        // Start fallback word stepper in case device TTS doesn't emit onRangeStart
        startFallbackWordStepper(cleanText, rate, onDone)

        val engine = tts
        if (engine != null && _playbackState.value.isReady) {
            val params = Bundle()
            engine.speak(cleanText, TextToSpeech.QUEUE_FLUSH, params, tag)
        }
    }

    private fun startFallbackWordStepper(
        text: String,
        rate: Float,
        onSimulatedDone: (() -> Unit)?
    ) {
        val wordRanges = Regex("\\S+").findAll(text).map { it.range.first to (it.range.last + 1) }.toList()
        if (wordRanges.isEmpty()) return
        fallbackHighlightJob = scope.launch {
            val msPerWord = (370f / rate).toLong().coerceIn(180L, 650L)
            for ((start, end) in wordRanges) {
                if (!isActive || receivedRangeStartForCurrentUtterance) break
                _playbackState.value = _playbackState.value.copy(
                    activeWordStart = start,
                    activeWordEnd = end
                )
                delay(msPerWord)
            }
            // If TTS engine didn't fire onDone (e.g. emulator without voice pack), clean up after fallback finishes
            if (!receivedRangeStartForCurrentUtterance && _playbackState.value.ttsUnavailableMessage != null) {
                if (onSimulatedDone != null) {
                    onSimulatedDone.invoke()
                } else {
                    _playbackState.value = _playbackState.value.copy(
                        isPlaying = false,
                        activeUtteranceTag = null,
                        activeWordStart = -1,
                        activeWordEnd = -1
                    )
                }
            }
        }
    }

    fun stopSpeaking() {
        sequenceJob?.cancel()
        fallbackHighlightJob?.cancel()
        currentUtteranceCompletedCallback = null
        tts?.stop()
        _playbackState.value = _playbackState.value.copy(
            isPlaying = false,
            activeUtteranceTag = null,
            activeLineIndex = -1,
            activeWordStart = -1,
            activeWordEnd = -1
        )
    }

    fun isSpeechRecognitionAvailable(): Boolean {
        return SpeechRecognizer.isRecognitionAvailable(context)
    }

    fun startPronunciationPractice(targetEnglish: String) {
        if (!isSpeechRecognitionAvailable()) {
            _pronunciationResult.value = PronunciationResult(
                isListening = false,
                errorMessage = "تشخیص گفتار سیستم روی این دستگاه فعال نیست؛ می‌توانید با دکمه «پخش و تکرار» همزمان با گوینده تمرین کنید."
            )
            return
        }
        try {
            speechRecognizer?.destroy()
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                setRecognitionListener(object : RecognitionListener {
                    override fun onReadyForSpeech(params: Bundle?) {
                        _pronunciationResult.value = PronunciationResult(isListening = true)
                    }

                    override fun onBeginningOfSpeech() {}
                    override fun onRmsChanged(rmsdB: Float) {}
                    override fun onBufferReceived(buffer: ByteArray?) {}
                    override fun onEndOfSpeech() {
                        _pronunciationResult.value = _pronunciationResult.value.copy(isListening = false)
                    }

                    override fun onError(error: Int) {
                        _pronunciationResult.value = PronunciationResult(
                            isListening = false,
                            errorMessage = "صدای واضحی دریافت نشد. نزدیک‌تر به میکروفون صحبت کنید یا از تمرین تکرار با گوینده استفاده کنید."
                        )
                    }

                    override fun onResults(results: Bundle?) {
                        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                        val spoken = matches?.firstOrNull().orEmpty()
                        val score = computeWordMatchScore(targetEnglish, spoken)
                        val feedback = when {
                            score >= 85 -> "عالی! تلفظ شما بسیار دقیق و نزدیک به گوینده اصلی بود."
                            score >= 55 -> "خوب بود! یک بار دیگر با سرعت آهسته‌تر گوش دهید و کلمات را واضح‌تر تکرار کنید."
                            else -> "بیایید دوباره تمرین کنیم! روی دکمه پخش آهسته بزنید و همزمان با تلفظ گوینده تکرار کنید."
                        }
                        _pronunciationResult.value = PronunciationResult(
                            isListening = false,
                            recognizedText = spoken,
                            scorePercent = score,
                            feedbackFa = feedback
                        )
                    }

                    override fun onPartialResults(partialResults: Bundle?) {}
                    override fun onEvent(eventType: Int, params: Bundle?) {}
                })
            }

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
            }
            _pronunciationResult.value = PronunciationResult(isListening = true)
            speechRecognizer?.startListening(intent)
        } catch (e: Exception) {
            Log.e(TAG, "SpeechRecognizer error", e)
            _pronunciationResult.value = PronunciationResult(
                isListening = false,
                errorMessage = "خطا در فعال‌سازی میکروفون برای تمرین تلفظ."
            )
        }
    }

    fun clearPronunciationResult() {
        speechRecognizer?.stopListening()
        _pronunciationResult.value = PronunciationResult()
    }

    private fun computeWordMatchScore(target: String, spoken: String): Int {
        val targetWords = target.lowercase().replace(Regex("[^a-z\\s]"), "").split(Regex("\\s+")).filter { it.isNotBlank() }
        val spokenWords = spoken.lowercase().replace(Regex("[^a-z\\s]"), "").split(Regex("\\s+")).filter { it.isNotBlank() }
        if (targetWords.isEmpty() || spokenWords.isEmpty()) return 0
        val matched = targetWords.count { tw -> spokenWords.any { sw -> sw == tw || levenshtein(sw, tw) <= 1 } }
        return ((matched * 100f) / targetWords.size).toInt().coerceIn(0, 100)
    }

    private fun levenshtein(a: String, b: String): Int {
        val dp = Array(a.length + 1) { IntArray(b.length + 1) }
        for (i in 0..a.length) dp[i][0] = i
        for (j in 0..b.length) dp[0][j] = j
        for (i in 1..a.length) {
            for (j in 1..b.length) {
                val cost = if (a[i - 1] == b[j - 1]) 0 else 1
                dp[i][j] = minOf(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + cost)
            }
        }
        return dp[a.length][b.length]
    }

    fun release() {
        sequenceJob?.cancel()
        fallbackHighlightJob?.cancel()
        tts?.stop()
        tts?.shutdown()
        speechRecognizer?.destroy()
        scope.cancel()
    }
}
