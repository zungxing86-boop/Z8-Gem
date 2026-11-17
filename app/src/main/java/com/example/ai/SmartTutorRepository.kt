package com.example.ai

import android.util.Log
import com.example.data.curriculum.Prospect2Catalog
import com.example.data.model.CurriculumUnit
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.content
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeout

private const val TAG = "SmartTutorRepository"

sealed interface TutorStreamState {
    data object Idle : TutorStreamState
    data object Loading : TutorStreamState
    data class Streaming(val text: String) : TutorStreamState
    data class Success(val text: String, val isOfflineFallback: Boolean = false) : TutorStreamState
    data class Error(val message: String) : TutorStreamState
}

class SmartTutorRepository {

    private val model by lazy {
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash",
            systemInstruction = content {
                text(
                    """
                    شما «معلم هوشمند و مهربان زبان انگلیسی پایه هشتم ایران (کتاب Prospect 2)» هستید.
                    مخاطب شما دانش‌آموز پایه هشتم (۱۳ تا ۱۴ ساله) در محیط خانه یا مدرسه است.
                    اصول تدریس شما:
                    1. پاسخ‌ها را به زبان فارسی روان، صمیمی، کوتاه و مناسب نمایشگر تلویزیون بنویسید.
                    2. تمام مثال‌ها و جملات انگلیسی را با ترجمه فارسی و تلفظ ساده بنویسید.
                    3. به جای دادن فوری جواب تمرین، ابتدا راهنمایی گام‌به‌گام و قاعده آموزشی بدهید.
                    4. برای گرامر و لغات، علاوه بر محتوای کتاب Prospect 2، مثال‌های جدید و کاربردی خارج از کتاب هم ارائه دهید.
                    5. نقاط ضعف دانش‌آموز را با تشویق و تمرین ساده اصلاح کنید.
                    """.trimIndent()
                )
            }
        )
    }

    fun askTutorStream(
        questionFa: String,
        currentUnit: CurriculumUnit?,
        weaknessSummary: String,
        forceOfflineMode: Boolean = false
    ): Flow<TutorStreamState> = flow {
        if (forceOfflineMode) {
            emit(TutorStreamState.Success(buildOfflineTutorAnswer(questionFa, currentUnit, weaknessSummary), isOfflineFallback = true))
            return@flow
        }

        emit(TutorStreamState.Loading)
        val prompt = buildString {
            if (currentUnit != null) {
                appendLine("درس فعلی دانش‌آموز: ${currentUnit.numberLabel} - ${currentUnit.titleEn} (${currentUnit.titleFa})")
                appendLine("موضوع و کاربرد: ${currentUnit.functionFa}")
            }
            if (weaknessSummary.isNotBlank()) {
                appendLine("نقاط ضعف ثبت‌شده دانش‌آموز در دستگاه: $weaknessSummary")
            }
            appendLine("پرسش یا درخواست دانش‌آموز:")
            append(questionFa)
        }

        val accumulated = StringBuilder()
        try {
            withTimeout(60_000L) {
                model.generateContentStream(prompt).collect { chunk ->
                    val piece = chunk.text.orEmpty()
                    if (piece.isNotEmpty()) {
                        accumulated.append(piece)
                        emit(TutorStreamState.Streaming(accumulated.toString()))
                    }
                }
            }
            if (accumulated.isNotBlank()) {
                emit(TutorStreamState.Success(accumulated.toString(), isOfflineFallback = false))
            } else {
                emit(TutorStreamState.Error("پاسخی از معلم هوشمند دریافت نشد. لطفاً دوباره تلاش کنید یا از حالت آفلاین معلم استفاده کنید."))
            }
        } catch (e: TimeoutCancellationException) {
            Log.e(TAG, "Firebase AI generation timed out", e)
            emit(TutorStreamState.Error("زمان پاسخ‌گویی به پایان رسید (اتصال اینترنت ضعیف است). می‌توانید از دکمه «پاسخ آفلاین معلم» استفاده کنید."))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Firebase AI generation failed", e)
            emit(
                TutorStreamState.Error(
                    "اتصال به معلم هوشمند ابری برقرار نشد (${e.localizedMessage ?: "خطای شبکه"}). برای ادامه بدون اینترنت می‌توانید «پاسخ آفلاین معلم» را انتخاب کنید."
                )
            )
        }
    }

    fun buildOfflineTutorAnswer(
        questionFa: String,
        currentUnit: CurriculumUnit?,
        weaknessSummary: String
    ): String {
        val unit = currentUnit ?: Prospect2Catalog.units.first()
        val grammar = unit.grammarTopics.firstOrNull()
        val vocabSamples = Prospect2Catalog.wordsForUnit(unit.id).take(4)

        return buildString {
            appendLine("📚 راهنمای آفلاین معلم — ${unit.numberLabel}: ${unit.titleFa} (${unit.titleEn})")
            appendLine()
            if (questionFa.contains("نقاط ضعف") || questionFa.contains("اشکال")) {
                appendLine("🎯 تحلیل نقاط ضعف ثبت‌شده روی دستگاه:")
                appendLine(weaknessSummary.ifBlank { "تا این لحظه خطای ثبت‌شده‌ای ندارید! آفرین، مسیر درس‌ها را ادامه دهید." })
                appendLine()
            }
            if (grammar != null) {
                appendLine("💡 نکته کلیدی گرامر (${grammar.titleFa}):")
                appendLine("فرمول: ${grammar.formulaEn}")
                grammar.stepByStepExplanationFa.take(2).forEach { appendLine("• $it") }
                appendLine()
                appendLine("✨ مثال‌های بیشتر خارج از کتاب:")
                grammar.extraExamples.forEach {
                    appendLine("• ${it.english}\n  ← ${it.persian}")
                }
                appendLine()
            }
            if (vocabSamples.isNotEmpty()) {
                appendLine("📖 واژگان مهم این بخش برای مرور:")
                vocabSamples.forEach {
                    appendLine("• ${it.english} (${it.phonetic}) = ${it.persian}")
                }
            }
            appendLine()
            append("💬 نکته: هر زمان اینترنت متصل باشد، می‌توانید پاسخ کاملاً اختصاصی‌تر از هوش مصنوعی معلم بگیرید.")
        }
    }
}
