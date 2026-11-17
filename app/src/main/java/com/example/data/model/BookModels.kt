package com.example.data.model

import androidx.compose.ui.graphics.Color

enum class UnitType {
    LESSON,
    REVIEW
}

data class CurriculumUnit(
    val id: Int,
    val type: UnitType,
    val numberLabel: String,
    val titleEn: String,
    val titleFa: String,
    val themeFa: String,
    val functionEn: String,
    val functionFa: String,
    val pageRange: String,
    val accentColor: Color,
    val keyExpressions: List<ExpressionPair>,
    val conversations: List<ConversationSection>,
    val phonicsSpotlights: List<PhonicsSpotlight>,
    val grammarTopics: List<GrammarTopic>,
    val exercises: List<BookExercise>,
    val quizQuestions: List<QuizQuestion>
)

data class ExpressionPair(
    val english: String,
    val persian: String
)

data class ConversationSection(
    val id: String,
    val titleEn: String,
    val titleFa: String,
    val contextFa: String,
    val bookPage: Int,
    val lines: List<ConversationLine>
)

data class ConversationLine(
    val speaker: String,
    val english: String,
    val persian: String
)

data class VocabWord(
    val id: String,
    val lessonId: Int,
    val english: String,
    val persian: String,
    val phonetic: String,
    val categoryFa: String,
    val exampleEn: String,
    val exampleFa: String,
    val bookPage: Int
)

data class PhonicsSpotlight(
    val id: String,
    val lessonId: Int,
    val pattern: String,
    val ipaHint: String,
    val explanationFa: String,
    val mistakeWarningFa: String,
    val sampleWords: List<PhonicsWord>
)

data class PhonicsWord(
    val word: String,
    val persian: String,
    val phonetic: String
)

data class GrammarTopic(
    val id: String,
    val lessonId: Int,
    val titleFa: String,
    val titleEn: String,
    val stepByStepExplanationFa: List<String>,
    val formulaEn: String,
    val bookExamples: List<ExpressionPair>,
    val extraExamples: List<ExpressionPair>,
    val miniChecks: List<GrammarMiniCheck>
)

data class GrammarMiniCheck(
    val id: String,
    val questionFa: String,
    val promptEn: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanationRightFa: String,
    val explanationWrongFa: String
)

enum class ExerciseCategory {
    CROSSWORD,
    PAIR_CARD,
    TABLE_FILL,
    SPELLING_CHECK,
    COMPREHENSION,
    REVIEW_CHECKLIST
}

data class BookExercise(
    val id: String,
    val lessonId: Int,
    val category: ExerciseCategory,
    val titleFa: String,
    val bookPage: Int,
    val instructionFa: String,
    val questionEn: String,
    val options: List<String>,
    val correctAnswer: String,
    val stepHintsFa: List<String>,
    val ruleTrickFa: String,
    val fullExplanationFa: String
)

data class QuizQuestion(
    val id: String,
    val lessonId: Int,
    val topicTag: String,
    val promptFa: String,
    val promptEn: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanationFa: String
)
