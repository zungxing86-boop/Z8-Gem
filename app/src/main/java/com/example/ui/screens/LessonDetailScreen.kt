package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.PlaybackState
import com.example.data.curriculum.Prospect2Catalog
import com.example.data.model.*
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.TutorDashboardState

@Composable
fun LessonDetailScreen(
    unit: CurriculumUnit,
    initialStage: Int,
    dashboardState: TutorDashboardState,
    playbackState: PlaybackState,
    isWideScreen: Boolean,
    onBack: () -> Unit,
    onStageChanged: (Int) -> Unit,
    onMarkStageDone: (Int) -> Unit,
    onPlayText: (text: String, tag: String, lineIndex: Int) -> Unit,
    onPlayConversation: (List<ConversationLine>, Int) -> Unit,
    onStopSpeech: () -> Unit,
    onSetSpeechRate: (Float) -> Unit,
    onTogglePersian: (Boolean) -> Unit,
    onRecordGrammarMistake: (topicTitle: String, userAns: String, correct: String) -> Unit,
    onRecordExercise: (ex: BookExercise, solved: Boolean, hintsUsed: Int, userAns: String) -> Unit,
    onRecordQuiz: (score: Int, total: Int, mistakes: List<Triple<String, String, String>>) -> Unit,
    onAskTutorAboutLesson: (String) -> Unit,
    onOpenNextUnit: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedStage by remember(unit.id, initialStage) {
        mutableIntStateOf(initialStage.coerceIn(0, 4))
    }

    val stages = remember(unit.type) {
        if (unit.type == UnitType.REVIEW) {
            listOf("عبارات کلیدی", "واژگان مرور", "تلفظ و هجی", "تمرین‌های کتاب", "آزمون جامع")
        } else {
            listOf("۱. مکالمه و خواندن", "۲. واژگان و تلفظ", "۳. گرامر گام‌به‌گام", "۴. حل تمرین کتاب", "۵. آزمون درس")
        }
    }

    LaunchedEffect(unit.id, selectedStage) {
        onStageChanged(selectedStage)
    }

    BackHandler {
        if (selectedStage > 0) {
            selectedStage = 0
        } else {
            onStopSpeech()
            onBack()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = if (isWideScreen) 28.dp else 14.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                TvSecondaryButton(
                    text = "بازگشت",
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    accentColor = unit.accentColor,
                    onClick = {
                        onStopSpeech()
                        onBack()
                    },
                    modifier = Modifier.testTag("lesson_back_button")
                )
                Column {
                    Text(
                        text = "${unit.numberLabel} • ${unit.titleFa} (${unit.pageRange})",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimaryLight
                    )
                    LtrEnglishText(
                        text = "${unit.titleEn} — ${unit.functionEn}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = EnglishDisplayFontFamily,
                            color = unit.accentColor
                        )
                    )
                }
            }

            if (isWideScreen) {
                SpeechSpeedControls(
                    speechRate = playbackState.speechRate,
                    isPlaying = playbackState.isPlaying,
                    onSetRate = onSetSpeechRate,
                    onStop = onStopSpeech
                )
            }
        }

        // Stage Tabs (D-pad focusable)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(stages) { index, label ->
                val isSelected = selectedStage == index
                val progressEntity = dashboardState.lessonProgressMap[unit.id]
                val isCompleted = when (index) {
                    0 -> progressEntity?.conversationDone == true
                    1 -> progressEntity?.vocabDone == true
                    2 -> progressEntity?.grammarDone == true
                    3 -> progressEntity?.exercisesDone == true
                    4 -> (progressEntity?.quizAttempts ?: 0) > 0
                    else -> false
                }

                TvFocusableCard(
                    onClick = {
                        onStopSpeech()
                        selectedStage = index
                    },
                    accentColor = unit.accentColor,
                    containerColor = if (isSelected) unit.accentColor.copy(alpha = 0.24f) else DeepSlateSurface,
                    focusedContainerColor = unit.accentColor.copy(alpha = 0.34f),
                    cornerRadius = 14.dp,
                    modifier = Modifier.testTag("lesson_stage_tab_$index")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        if (isCompleted) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "تکمیل شده",
                                tint = EmeraldMintTertiary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (isSelected) TextPrimaryLight else TextSecondaryMuted
                        )
                    }
                }
            }
        }

        // Stage Content
        Box(modifier = Modifier.weight(1f)) {
            when (selectedStage) {
                0 -> ConversationStage(
                    unit = unit,
                    playbackState = playbackState,
                    showPersian = dashboardState.preferences.showPersianTranslation,
                    onTogglePersian = onTogglePersian,
                    onPlayLine = { text, idx -> onPlayText(text, "conv_line_$idx", idx) },
                    onPlayAll = { lines -> onPlayConversation(lines, 0) },
                    onStop = onStopSpeech,
                    onCompleteAndNext = {
                        onMarkStageDone(0)
                        selectedStage = 1
                    }
                )
                1 -> VocabularyAndPhonicsStage(
                    unit = unit,
                    playbackState = playbackState,
                    onPlayWord = { text, tag -> onPlayText(text, tag, -1) },
                    onCompleteAndNext = {
                        onMarkStageDone(1)
                        selectedStage = 2
                    }
                )
                2 -> GrammarStage(
                    unit = unit,
                    playbackState = playbackState,
                    onPlayExample = { text, tag -> onPlayText(text, tag, -1) },
                    onRecordMistake = onRecordGrammarMistake,
                    onAskTutor = { topic ->
                        onAskTutorAboutLesson("لطفاً نکته گرامری «${topic.titleFa}» را ساده‌تر توضیح بده و ۳ مثال جدید خارج از کتاب بزن.")
                    },
                    onCompleteAndNext = {
                        onMarkStageDone(2)
                        selectedStage = 3
                    }
                )
                3 -> ExercisesStage(
                    unit = unit,
                    dashboardState = dashboardState,
                    onRecordExercise = onRecordExercise,
                    onAskTutor = { ex ->
                        onAskTutorAboutLesson("در تمرین «${ex.titleFa}» مشکل دارم. بدون اینکه فقط جواب بدهی، قدم‌به‌قدم توضیح بده چطور حلش کنم؟")
                    },
                    onCompleteAndNext = {
                        onMarkStageDone(3)
                        selectedStage = 4
                    }
                )
                4 -> QuizStage(
                    unit = unit,
                    onRecordQuiz = onRecordQuiz,
                    onAskTutorWeakness = { summary ->
                        onAskTutorAboutLesson("در آزمون پایان ${unit.numberLabel} این مباحث را اشتباه پاسخ دادم: $summary. لطفاً دوباره آموزش بده و تمرین مشابه بده.")
                    },
                    onOpenNextUnit = onOpenNextUnit
                )
            }
        }
    }
}

@Composable
private fun SpeechSpeedControls(
    speechRate: Float,
    isPlaying: Boolean,
    onSetRate: (Float) -> Unit,
    onStop: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val speeds = listOf(0.75f to "آهسته", 0.95f to "عادی", 1.2f to "سریع")
        speeds.forEach { (rate, title) ->
            val selected = kotlin.math.abs(speechRate - rate) < 0.1f
            TvSecondaryButton(
                text = title,
                accentColor = if (selected) AmberGoldSecondary else TextTertiarySubtle,
                onClick = { onSetRate(rate) }
            )
        }
        if (isPlaying) {
            TvActionButton(
                text = "توقف صوت",
                icon = Icons.Default.Stop,
                containerColor = CoralError,
                onClick = onStop
            )
        }
    }
}

@Composable
private fun ConversationStage(
    unit: CurriculumUnit,
    playbackState: PlaybackState,
    showPersian: Boolean,
    onTogglePersian: (Boolean) -> Unit,
    onPlayLine: (String, Int) -> Unit,
    onPlayAll: (List<ConversationLine>) -> Unit,
    onStop: () -> Unit,
    onCompleteAndNext: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DeepSlateSurface),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "عبارات کلیدی ${unit.numberLabel}",
                            style = MaterialTheme.typography.titleLarge,
                            color = unit.accentColor
                        )
                        TvSecondaryButton(
                            text = if (showPersian) "مخفی کردن ترجمه" else "نمایش ترجمه فارسی",
                            icon = Icons.Default.Translate,
                            onClick = { onTogglePersian(!showPersian) }
                        )
                    }

                    unit.keyExpressions.forEachIndexed { idx, expr ->
                        TvFocusableCard(
                            onClick = { onPlayLine(expr.english, -10 - idx) },
                            accentColor = unit.accentColor,
                            containerColor = ElevatedCardSurface
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    LtrEnglishText(text = expr.english)
                                    if (showPersian) {
                                        Text(
                                            text = expr.persian,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = TextTertiarySubtle
                                        )
                                    }
                                }
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "پخش تلفظ",
                                    tint = unit.accentColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        unit.conversations.forEach { conv ->
            item(key = conv.id) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = DeepSlateSurface),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${conv.titleFa} (صفحه ${conv.bookPage})",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = TextPrimaryLight
                                )
                                Text(
                                    text = conv.contextFa,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextTertiarySubtle
                                )
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TvActionButton(
                                    text = if (playbackState.isPlaying) "توقف" else "پخش کامل با هایلایت",
                                    icon = if (playbackState.isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                                    containerColor = if (playbackState.isPlaying) CoralError else unit.accentColor,
                                    onClick = {
                                        if (playbackState.isPlaying) onStop() else onPlayAll(conv.lines)
                                    },
                                    modifier = Modifier.testTag("play_all_${conv.id}")
                                )
                            }
                        }

                        if (!playbackState.ttsUnavailableMessage.isNullOrBlank()) {
                            Text(
                                text = playbackState.ttsUnavailableMessage,
                                style = MaterialTheme.typography.labelMedium,
                                color = AmberGoldSecondary
                            )
                        }

                        conv.lines.forEachIndexed { idx, line ->
                            val isLineActive = playbackState.isPlaying && playbackState.activeLineIndex == idx
                            TvFocusableCard(
                                onClick = { onPlayLine(line.english, idx) },
                                accentColor = AmberGoldSecondary,
                                containerColor = if (isLineActive) HighlightCardSurface else ElevatedCardSurface
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = unit.accentColor.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = line.speaker,
                                            style = MaterialTheme.typography.labelLarge,
                                            color = unit.accentColor,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                        )
                                    }
                                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        SyncedSpeechLine(
                                            text = line.english,
                                            isLineActive = isLineActive,
                                            activeWordStart = playbackState.activeWordStart,
                                            activeWordEnd = playbackState.activeWordEnd
                                        )
                                        if (showPersian) {
                                            Text(
                                                text = line.persian,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = TextTertiarySubtle
                                            )
                                        }
                                    }
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                        contentDescription = "پخش خط مکالمه",
                                        tint = if (isLineActive) AmberGoldSecondary else TextTertiarySubtle
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TvActionButton(
                    text = "ثبت مطالعه مکالمه و ادامه به واژگان",
                    icon = Icons.Default.Check,
                    containerColor = EmeraldMintTertiary,
                    onClick = onCompleteAndNext,
                    modifier = Modifier.testTag("complete_stage_0")
                )
            }
        }
    }
}

@Composable
private fun VocabularyAndPhonicsStage(
    unit: CurriculumUnit,
    playbackState: PlaybackState,
    onPlayWord: (String, String) -> Unit,
    onCompleteAndNext: () -> Unit
) {
    val words = remember(unit.id) { Prospect2Catalog.wordsForUnit(unit.id) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        if (unit.phonicsSpotlights.isNotEmpty()) {
            item {
                SectionHeaderBadge(
                    title = "املا و تلفظ ویژه (Spelling & Pronunciation)",
                    subtitle = "آموزش دونویسه‌ها و خوشه‌های همخوان کتاب",
                    accentColor = AmberGoldSecondary
                )
            }
            items(unit.phonicsSpotlights, key = { it.id }) { spotlight ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = DeepSlateSurface),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = AmberGoldContainer
                            ) {
                                LtrEnglishText(
                                    text = spotlight.pattern,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontFamily = EnglishDisplayFontFamily,
                                        color = AmberGoldSecondary
                                    ),
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                            Text(
                                text = spotlight.ipaHint,
                                style = MaterialTheme.typography.labelLarge,
                                color = SkyTealPrimary
                            )
                        }
                        Text(
                            text = spotlight.explanationFa,
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextPrimaryLight
                        )
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = CoralErrorContainer.copy(alpha = 0.7f)
                        ) {
                            Text(
                                text = "⚠️ هشدار تلفظ رایج: ${spotlight.mistakeWarningFa}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimaryLight,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            items(spotlight.sampleWords) { pw ->
                                TvFocusableCard(
                                    onClick = { onPlayWord(pw.word, "ph_${pw.word}") },
                                    accentColor = AmberGoldSecondary
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        LtrEnglishText(text = pw.word)
                                        Text(
                                            text = pw.phonetic,
                                            style = MaterialTheme.typography.labelMedium,
                                            color = SkyTealPrimary
                                        )
                                        Text(
                                            text = pw.persian,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = TextSecondaryMuted
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            SectionHeaderBadge(
                title = "واژگان و عبارات استخراج‌شده (${words.size} مورد)",
                subtitle = "شامل واژگان درس و دیکشنری تصویری (Photo Dictionary) — برای شنیدن تلفظ روی کارت کلیک کنید",
                accentColor = unit.accentColor
            )
        }

        items(words, key = { it.id }) { word ->
            val isSpeakingThis = playbackState.isPlaying && playbackState.activeUtteranceTag == "vocab_${word.id}"
            TvFocusableCard(
                onClick = { onPlayWord("${word.english}. ${word.exampleEn}", "vocab_${word.id}") },
                accentColor = unit.accentColor,
                containerColor = if (isSpeakingThis) HighlightCardSurface else ElevatedCardSurface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            LtrEnglishText(
                                text = word.english,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontFamily = EnglishDisplayFontFamily,
                                    fontSize = 20.sp,
                                    color = unit.accentColor
                                )
                            )
                            Text(
                                text = word.phonetic,
                                style = MaterialTheme.typography.labelMedium,
                                color = TextTertiarySubtle
                            )
                        }
                        Text(
                            text = "${word.persian}  •  دسته: ${word.categoryFa} (ص ${word.bookPage})",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextPrimaryLight
                        )
                        LtrEnglishText(
                            text = word.exampleEn,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = EnglishBodyFontFamily,
                                color = TextSecondaryMuted
                            )
                        )
                        Text(
                            text = word.exampleFa,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextTertiarySubtle
                        )
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "پخش تلفظ کلمه",
                        tint = if (isSpeakingThis) AmberGoldSecondary else unit.accentColor,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TvActionButton(
                    text = "تکمیل واژگان و ادامه به گرامر",
                    icon = Icons.Default.Check,
                    containerColor = EmeraldMintTertiary,
                    onClick = onCompleteAndNext,
                    modifier = Modifier.testTag("complete_stage_1")
                )
            }
        }
    }
}

@Composable
private fun GrammarStage(
    unit: CurriculumUnit,
    playbackState: PlaybackState,
    onPlayExample: (String, String) -> Unit,
    onRecordMistake: (topicTitle: String, userAns: String, correct: String) -> Unit,
    onAskTutor: (GrammarTopic) -> Unit,
    onCompleteAndNext: () -> Unit
) {
    if (unit.grammarTopics.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "این بخش یک مرور جامع است؛ نکات گرامری در درس‌های مرتبط آموزش داده شده‌اند.",
                style = MaterialTheme.typography.titleMedium,
                color = TextSecondaryMuted
            )
            Spacer(Modifier.height(16.dp))
            TvActionButton(
                text = "ادامه به حل تمرین‌های کتاب",
                onClick = onCompleteAndNext
            )
        }
        return
    }

    val selectedOptions = remember { mutableStateMapOf<String, Int>() }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        items(unit.grammarTopics, key = { it.id }) { topic ->
            Card(
                colors = CardDefaults.cardColors(containerColor = DeepSlateSurface),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = topic.titleFa,
                        style = MaterialTheme.typography.headlineSmall,
                        color = unit.accentColor
                    )
                    LtrEnglishText(
                        text = topic.titleEn,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = EnglishDisplayFontFamily,
                            color = TextTertiarySubtle
                        )
                    )

                    // Formula Box
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = SkyTealContainer.copy(alpha = 0.6f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        LtrEnglishText(
                            text = topic.formulaEn,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = EnglishDisplayFontFamily,
                                color = AmberGoldSecondary
                            ),
                            modifier = Modifier.padding(14.dp)
                        )
                    }

                    // Step by Step explanation
                    Text("آموزش ساده و مرحله‌به‌مرحله:", style = MaterialTheme.typography.titleMedium, color = TextPrimaryLight)
                    topic.stepByStepExplanationFa.forEach { step ->
                        Text(text = "• $step", style = MaterialTheme.typography.bodyLarge, color = TextSecondaryMuted)
                    }

                    // Book Examples
                    Text("مثال‌های کتاب درسی:", style = MaterialTheme.typography.titleMedium, color = SkyTealPrimary)
                    topic.bookExamples.forEachIndexed { idx, pair ->
                        TvFocusableCard(
                            onClick = { onPlayExample(pair.english, "gb_${topic.id}_$idx") },
                            accentColor = SkyTealPrimary
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    LtrEnglishText(text = pair.english)
                                    Text(text = pair.persian, style = MaterialTheme.typography.bodyMedium, color = TextTertiarySubtle)
                                }
                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "پخش مثال", tint = SkyTealPrimary)
                            }
                        }
                    }

                    // Extra Outside Examples
                    Text("مثال‌های بیشتر خارج از کتاب (برای درک عمیق‌تر):", style = MaterialTheme.typography.titleMedium, color = AmberGoldSecondary)
                    topic.extraExamples.forEachIndexed { idx, pair ->
                        TvFocusableCard(
                            onClick = { onPlayExample(pair.english, "ge_${topic.id}_$idx") },
                            accentColor = AmberGoldSecondary,
                            containerColor = AmberGoldContainer.copy(alpha = 0.25f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    LtrEnglishText(text = pair.english)
                                    Text(text = pair.persian, style = MaterialTheme.typography.bodyMedium, color = TextSecondaryMuted)
                                }
                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "پخش مثال تکمیلی", tint = AmberGoldSecondary)
                            }
                        }
                    }

                    // Interactive Mini-Checks
                    Text("تمرین اطمینان از یادگیری:", style = MaterialTheme.typography.titleMedium, color = EmeraldMintTertiary)
                    topic.miniChecks.forEach { check ->
                        val chosen = selectedOptions[check.id]
                        Card(
                            colors = CardDefaults.cardColors(containerColor = ElevatedCardSurface),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Text(text = check.questionFa, style = MaterialTheme.typography.bodyLarge, color = TextPrimaryLight)
                                LtrEnglishText(text = check.promptEn)

                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    check.options.forEachIndexed { idx, opt ->
                                        val isPicked = chosen == idx
                                        val isCorrect = idx == check.correctIndex
                                        val btnColor = when {
                                            chosen == null -> DeepSlateSurface
                                            isPicked && isCorrect -> EmeraldMintContainer
                                            isPicked && !isCorrect -> CoralErrorContainer
                                            else -> DeepSlateSurface
                                        }
                                        TvFocusableCard(
                                            onClick = {
                                                selectedOptions[check.id] = idx
                                                if (idx != check.correctIndex) {
                                                    onRecordMistake(topic.titleFa, opt, check.options[check.correctIndex])
                                                }
                                            },
                                            accentColor = if (isPicked && isCorrect) EmeraldMintTertiary else unit.accentColor,
                                            containerColor = btnColor,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(12.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                LtrEnglishText(text = opt)
                                            }
                                        }
                                    }
                                }

                                if (chosen != null) {
                                    val isRight = chosen == check.correctIndex
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isRight) EmeraldMintContainer else CoralErrorContainer
                                    ) {
                                        Text(
                                            text = if (isRight) "✅ ${check.explanationRightFa}" else "❌ ${check.explanationWrongFa}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = TextPrimaryLight,
                                            modifier = Modifier.padding(12.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        TvSecondaryButton(
                            text = "سؤال و مثال بیشتر از معلم هوشمند",
                            icon = Icons.Default.Psychology,
                            accentColor = AmberGoldSecondary,
                            onClick = { onAskTutor(topic) }
                        )
                    }
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TvActionButton(
                    text = "تکمیل گرامر و ادامه به حل تمرین کتاب",
                    icon = Icons.Default.Check,
                    containerColor = EmeraldMintTertiary,
                    onClick = onCompleteAndNext,
                    modifier = Modifier.testTag("complete_stage_2")
                )
            }
        }
    }
}

@Composable
private fun ExercisesStage(
    unit: CurriculumUnit,
    dashboardState: TutorDashboardState,
    onRecordExercise: (ex: BookExercise, solved: Boolean, hintsUsed: Int, userAns: String) -> Unit,
    onAskTutor: (BookExercise) -> Unit,
    onCompleteAndNext: () -> Unit
) {
    val hintStepsRevealed = remember { mutableStateMapOf<String, Int>() }
    val selectedAnswers = remember { mutableStateMapOf<String, String>() }
    val revealedSolution = remember { mutableStateMapOf<String, Boolean>() }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            SectionHeaderBadge(
                title = "حل گام‌به‌گام تمرین‌ها و فعالیت‌های کتاب",
                subtitle = "به جای نمایش فوری جواب، ابتدا تلاش کنید یا از راهنمای پله‌پله و ترفند حل استفاده کنید",
                accentColor = unit.accentColor
            )
        }

        items(unit.exercises, key = { it.id }) { ex ->
            val hintsShown = hintStepsRevealed[ex.id] ?: 0
            val userChoice = selectedAnswers[ex.id]
            val isSolvedPreviously = dashboardState.exerciseAttemptMap[ex.id]?.solved == true
            val isCorrectNow = userChoice == ex.correctAnswer
            val showSolution = revealedSolution[ex.id] == true || isCorrectNow

            Card(
                colors = CardDefaults.cardColors(containerColor = DeepSlateSurface),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${ex.titleFa} (صفحه ${ex.bookPage})",
                            style = MaterialTheme.typography.titleLarge,
                            color = unit.accentColor
                        )
                        if (isSolvedPreviously || isCorrectNow) {
                            Surface(shape = RoundedCornerShape(50), color = EmeraldMintContainer) {
                                Text(
                                    text = "حل شده ✓",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = EmeraldMintTertiary,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Text(text = ex.instructionFa, style = MaterialTheme.typography.bodyLarge, color = TextPrimaryLight)
                    Surface(shape = RoundedCornerShape(12.dp), color = ElevatedCardSurface) {
                        LtrEnglishText(text = ex.questionEn, modifier = Modifier.padding(14.dp))
                    }

                    // Options
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ex.options.forEach { option ->
                            val picked = userChoice == option
                            val right = option == ex.correctAnswer
                            val cardColor = when {
                                !picked -> ElevatedCardSurface
                                right -> EmeraldMintContainer
                                else -> CoralErrorContainer
                            }
                            TvFocusableCard(
                                onClick = {
                                    selectedAnswers[ex.id] = option
                                    onRecordExercise(ex, right, hintsShown, option)
                                },
                                accentColor = if (picked && right) EmeraldMintTertiary else unit.accentColor,
                                containerColor = cardColor
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    LtrEnglishText(text = option, modifier = Modifier.weight(1f))
                                    if (picked) {
                                        Icon(
                                            imageVector = if (right) Icons.Default.CheckCircle else Icons.Default.Cancel,
                                            contentDescription = null,
                                            tint = if (right) EmeraldMintTertiary else CoralError
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (userChoice != null && !isCorrectNow) {
                        Surface(shape = RoundedCornerShape(10.dp), color = CoralErrorContainer) {
                            Text(
                                text = "هنوز دقیق نیست! قبل از دیدن پاسخ نهایی، از دکمه «راهنمای گام‌به‌گام» کمک بگیر و دوباره تلاش کن.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimaryLight,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    // Step-by-step hints revealed progressively
                    if (hintsShown > 0) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            ex.stepHintsFa.take(hintsShown).forEach { hint ->
                                Surface(shape = RoundedCornerShape(10.dp), color = AmberGoldContainer.copy(alpha = 0.55f)) {
                                    Text(
                                        text = "💡 $hint",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextPrimaryLight,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            }
                            if (hintsShown > ex.stepHintsFa.size) {
                                Surface(shape = RoundedCornerShape(10.dp), color = SkyTealContainer) {
                                    Text(
                                        text = "🎯 قاعده و ترفند مشابه: ${ex.ruleTrickFa}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextPrimaryLight,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            }
                        }
                    }

                    AnimatedVisibility(visible = showSolution) {
                        Surface(shape = RoundedCornerShape(12.dp), color = EmeraldMintContainer.copy(alpha = 0.85f)) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text("✅ پاسخ صحیح و روش رسیدن به آن:", style = MaterialTheme.typography.titleMedium, color = EmeraldMintTertiary)
                                LtrEnglishText(text = ex.correctAnswer)
                                Text(text = ex.fullExplanationFa, style = MaterialTheme.typography.bodyMedium, color = TextPrimaryLight)
                                Text(text = "📌 نکته ترفند: ${ex.ruleTrickFa}", style = MaterialTheme.typography.bodyMedium, color = AmberGoldSecondary)
                            }
                        }
                    }

                    // Hint / Retry / Ask Tutor Buttons
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (hintsShown <= ex.stepHintsFa.size) {
                            TvSecondaryButton(
                                text = if (hintsShown == 0) "راهنمای گام‌به‌گام (گام ۱)" else "راهنمای مرحله بعد",
                                icon = Icons.Default.Lightbulb,
                                accentColor = AmberGoldSecondary,
                                onClick = { hintStepsRevealed[ex.id] = hintsShown + 1 }
                            )
                        } else if (!showSolution) {
                            TvSecondaryButton(
                                text = "نمایش پاسخ نهایی و توضیح کامل",
                                icon = Icons.Default.Visibility,
                                accentColor = EmeraldMintTertiary,
                                onClick = { revealedSolution[ex.id] = true }
                            )
                        }

                        TvSecondaryButton(
                            text = "کمک از معلم هوشمند",
                            icon = Icons.Default.Psychology,
                            accentColor = SkyTealPrimary,
                            onClick = { onAskTutor(ex) }
                        )
                    }
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TvActionButton(
                    text = "تکمیل تمرین‌ها و شروع آزمون پایان درس",
                    icon = Icons.Default.Quiz,
                    containerColor = AmberGoldSecondary,
                    onClick = onCompleteAndNext,
                    modifier = Modifier.testTag("complete_stage_3")
                )
            }
        }
    }
}

@Composable
private fun QuizStage(
    unit: CurriculumUnit,
    onRecordQuiz: (score: Int, total: Int, mistakes: List<Triple<String, String, String>>) -> Unit,
    onAskTutorWeakness: (String) -> Unit,
    onOpenNextUnit: (Int) -> Unit
) {
    val questions = unit.quizQuestions
    var currentIdx by remember(unit.id) { mutableIntStateOf(0) }
    var selectedAnswerIdx by remember(unit.id, currentIdx) { mutableStateOf<Int?>(null) }
    var score by remember(unit.id) { mutableIntStateOf(0) }
    val mistakes = remember(unit.id) { mutableStateListOf<Triple<String, String, String>>() }
    var finished by remember(unit.id) { mutableStateOf(false) }

    if (questions.isEmpty()) return

    if (finished) {
        val nextId = Prospect2Catalog.nextUnitId(unit.id)
        val percent = (score * 100) / questions.size
        Card(
            colors = CardDefaults.cardColors(containerColor = DeepSlateSurface),
            shape = RoundedCornerShape(22.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "کارنامه آزمون ${unit.numberLabel}: $score از ${questions.size} ($percent٪)",
                    style = MaterialTheme.typography.headlineMedium,
                    color = if (percent >= 70) EmeraldMintTertiary else AmberGoldSecondary
                )
                Text(
                    text = if (percent >= 70) "آفرین! تسلط بسیار خوبی روی این درس داری." else "تلاش خوبی بود! معلم هوشمند نقاط ضعفت را ثبت کرد تا دوباره با هم مرور کنیم.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextPrimaryLight
                )

                if (mistakes.isNotEmpty()) {
                    Text("موارد نیازمند مرور:", style = MaterialTheme.typography.titleMedium, color = CoralError)
                    mistakes.forEach { (tag, prompt, correct) ->
                        Surface(shape = RoundedCornerShape(10.dp), color = CoralErrorContainer.copy(alpha = 0.65f)) {
                            Text(
                                text = "• [$tag] $prompt ← پاسخ درست: $correct",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimaryLight,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    TvSecondaryButton(
                        text = "آزمون مجدد",
                        icon = Icons.Default.Refresh,
                        onClick = {
                            currentIdx = 0
                            selectedAnswerIdx = null
                            score = 0
                            mistakes.clear()
                            finished = false
                        }
                    )
                    if (mistakes.isNotEmpty()) {
                        TvActionButton(
                            text = "آموزش مجدد نقاط ضعف با معلم",
                            icon = Icons.Default.Psychology,
                            containerColor = AmberGoldSecondary,
                            onClick = {
                                val summary = mistakes.joinToString("، ") { "${it.first}: ${it.second}" }
                                onAskTutorWeakness(summary)
                            }
                        )
                    }
                    if (nextId != null) {
                        TvActionButton(
                            text = "رفتن به درس بعدی",
                            icon = Icons.Default.ArrowForward,
                            containerColor = EmeraldMintTertiary,
                            onClick = { onOpenNextUnit(nextId) }
                        )
                    }
                }
            }
        }
        return
    }

    val q = questions[currentIdx]
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DeepSlateSurface),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "سؤال ${currentIdx + 1} از ${questions.size} • مبحث: ${q.topicTag}",
                            style = MaterialTheme.typography.titleMedium,
                            color = unit.accentColor
                        )
                        Text(
                            text = "امتیاز فعلی: $score",
                            style = MaterialTheme.typography.labelLarge,
                            color = AmberGoldSecondary
                        )
                    }

                    LinearProgressIndicator(
                        progress = { (currentIdx + 1).toFloat() / questions.size },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = unit.accentColor,
                        trackColor = SubtleOutline
                    )

                    Text(text = q.promptFa, style = MaterialTheme.typography.headlineSmall, color = TextPrimaryLight)
                    LtrEnglishText(text = q.promptEn)

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        q.options.forEachIndexed { idx, opt ->
                            val picked = selectedAnswerIdx == idx
                            val isRight = idx == q.correctIndex
                            val bg = when {
                                selectedAnswerIdx == null -> ElevatedCardSurface
                                isRight -> EmeraldMintContainer
                                picked -> CoralErrorContainer
                                else -> ElevatedCardSurface
                            }
                            TvFocusableCard(
                                onClick = {
                                    if (selectedAnswerIdx == null) {
                                        selectedAnswerIdx = idx
                                        if (isRight) {
                                            score++
                                        } else {
                                            mistakes.add(Triple(q.topicTag, q.promptFa, q.options[q.correctIndex]))
                                        }
                                    }
                                },
                                accentColor = if (selectedAnswerIdx != null && isRight) EmeraldMintTertiary else unit.accentColor,
                                containerColor = bg,
                                modifier = Modifier.testTag("quiz_option_$idx")
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    LtrEnglishText(text = opt, modifier = Modifier.weight(1f))
                                    if (selectedAnswerIdx != null && (isRight || picked)) {
                                        Icon(
                                            imageVector = if (isRight) Icons.Default.CheckCircle else Icons.Default.Cancel,
                                            contentDescription = null,
                                            tint = if (isRight) EmeraldMintTertiary else CoralError
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (selectedAnswerIdx != null) {
                        val isRight = selectedAnswerIdx == q.correctIndex
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isRight) EmeraldMintContainer else CoralErrorContainer
                        ) {
                            Text(
                                text = (if (isRight) "✅ آفرین! " else "❌ توضیح آموزشی: ") + q.explanationFa,
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextPrimaryLight,
                                modifier = Modifier.padding(14.dp)
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            TvActionButton(
                                text = if (currentIdx < questions.lastIndex) "سؤال بعدی" else "مشاهده کارنامه نهایی",
                                icon = Icons.AutoMirrored.Filled.ArrowBack,
                                onClick = {
                                    if (currentIdx < questions.lastIndex) {
                                        currentIdx++
                                        selectedAnswerIdx = null
                                    } else {
                                        onRecordQuiz(score, questions.size, mistakes.toList())
                                        finished = true
                                    }
                                },
                                modifier = Modifier.testTag("quiz_next_button")
                            )
                        }
                    }
                }
            }
        }
    }
}
