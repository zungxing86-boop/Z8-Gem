package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.curriculum.Prospect2Catalog
import com.example.data.model.VocabWord
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.TutorDashboardState

private enum class DictionaryMode(val titleFa: String) {
    BROWSE("واژه‌نامه هوشمند"),
    FLASHCARD("فلش‌کارت مرور هوشمند"),
    BIDIRECTIONAL_QUIZ("آزمون دوطرفه (EN ↔ FA)")
}

@Composable
fun DictionaryScreen(
    dashboardState: TutorDashboardState,
    initialWeakOnly: Boolean,
    isWideScreen: Boolean,
    buildReviewQueue: (Int?) -> List<VocabWord>,
    onPlayWord: (String) -> Unit,
    onRecordWordReview: (VocabWord, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var mode by remember(initialWeakOnly) {
        mutableStateOf(if (initialWeakOnly) DictionaryMode.FLASHCARD else DictionaryMode.BROWSE)
    }
    var selectedLessonFilter by remember { mutableStateOf<Int?>(null) } // null = all, -1 = weak only
    var weakOnlyFilter by remember(initialWeakOnly) { mutableStateOf(initialWeakOnly) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredWords = remember(selectedLessonFilter, weakOnlyFilter, searchQuery, dashboardState.wordMasteryMap) {
        Prospect2Catalog.allWords.filter { word ->
            val matchesLesson = selectedLessonFilter == null || word.lessonId == selectedLessonFilter
            val mastery = dashboardState.wordMasteryMap[word.id]
            val matchesWeak = !weakOnlyFilter || (mastery != null && (mastery.wrongCount > 0 && mastery.masteryLevel < 4))
            val matchesQuery = searchQuery.isBlank() ||
                word.english.contains(searchQuery, ignoreCase = true) ||
                word.persian.contains(searchQuery)
            matchesLesson && matchesWeak && matchesQuery
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = if (isWideScreen) 28.dp else 14.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionHeaderBadge(
            title = "آموزش واژگان و دیکشنری هوشمند",
            subtitle = "استخراج تمام لغات دروس و Photo Dictionary، تلفظ صوتی و تکرار هوشمند لغات فراموش‌شده",
            accentColor = SkyTealPrimary
        )

        // Mode Switcher
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(DictionaryMode.entries) { entry ->
                val active = mode == entry
                TvFocusableCard(
                    onClick = { mode = entry },
                    accentColor = SkyTealPrimary,
                    containerColor = if (active) SkyTealContainer else DeepSlateSurface,
                    cornerRadius = 12.dp,
                    modifier = Modifier.testTag("dict_mode_${entry.name}")
                ) {
                    Text(
                        text = entry.titleFa,
                        style = MaterialTheme.typography.labelLarge,
                        color = if (active) TextPrimaryLight else TextSecondaryMuted,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                }
            }
        }

        // Lesson & Weak Filter Row
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                TvFocusableCard(
                    onClick = {
                        selectedLessonFilter = null
                        weakOnlyFilter = false
                    },
                    accentColor = SkyTealPrimary,
                    containerColor = if (selectedLessonFilter == null && !weakOnlyFilter) SkyTealContainer else DeepSlateSurface,
                    cornerRadius = 10.dp
                ) {
                    Text(
                        text = "همه (${Prospect2Catalog.allWords.size})",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextPrimaryLight,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
            item {
                TvFocusableCard(
                    onClick = {
                        weakOnlyFilter = !weakOnlyFilter
                        if (weakOnlyFilter) selectedLessonFilter = null
                    },
                    accentColor = CoralError,
                    containerColor = if (weakOnlyFilter) CoralErrorContainer else DeepSlateSurface,
                    cornerRadius = 10.dp
                ) {
                    Text(
                        text = "لغات فراموش‌شده (${dashboardState.weakWords.size})",
                        style = MaterialTheme.typography.labelMedium,
                        color = if (weakOnlyFilter) TextPrimaryLight else CoralError,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
            items((1..7).toList()) { lessonNum ->
                val active = selectedLessonFilter == lessonNum && !weakOnlyFilter
                TvFocusableCard(
                    onClick = {
                        selectedLessonFilter = lessonNum
                        weakOnlyFilter = false
                    },
                    accentColor = SkyTealPrimary,
                    containerColor = if (active) SkyTealContainer else DeepSlateSurface,
                    cornerRadius = 10.dp
                ) {
                    Text(
                        text = "درس $lessonNum",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextPrimaryLight,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }

        when (mode) {
            DictionaryMode.BROWSE -> {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("جستجوی کلمه انگلیسی یا معنی فارسی...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("dictionary_search_input"),
                    shape = RoundedCornerShape(14.dp)
                )

                if (filteredWords.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = if (weakOnlyFilter) "تبریک! هیچ لغت فراموش‌شده‌ای در این بخش ثبت نشده است." else "موردی یافت نشد.",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextSecondaryMuted
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(bottom = 20.dp)
                    ) {
                        items(filteredWords, key = { it.id }) { word ->
                            val mastery = dashboardState.wordMasteryMap[word.id]
                            val level = mastery?.masteryLevel ?: 0
                            TvFocusableCard(
                                onClick = { onPlayWord("${word.english}. ${word.exampleEn}") },
                                accentColor = SkyTealPrimary
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
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
                                                    fontSize = 19.sp,
                                                    color = SkyTealPrimary
                                                )
                                            )
                                            Text(text = word.phonetic, style = MaterialTheme.typography.labelMedium, color = TextTertiarySubtle)
                                            Surface(
                                                shape = RoundedCornerShape(50),
                                                color = if (level >= 3) EmeraldMintContainer else DeepSlateSurface
                                            ) {
                                                Text(
                                                    text = "تسلط $level/۵",
                                                    style = MaterialTheme.typography.labelMedium,
                                                    color = if (level >= 3) EmeraldMintTertiary else AmberGoldSecondary,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = "${word.persian}  •  درس ${word.lessonId} (${word.categoryFa})",
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
                                    }
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                        contentDescription = "پخش تلفظ",
                                        tint = SkyTealPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            DictionaryMode.FLASHCARD -> {
                val queue = remember(selectedLessonFilter, weakOnlyFilter) {
                    val base = buildReviewQueue(selectedLessonFilter)
                    if (weakOnlyFilter && dashboardState.weakWords.isNotEmpty()) {
                        dashboardState.weakWords
                    } else {
                        base
                    }
                }
                SmartFlashcardDeck(
                    words = queue,
                    onPlayWord = onPlayWord,
                    onMarkKnown = { w -> onRecordWordReview(w, true) },
                    onMarkForgotten = { w -> onRecordWordReview(w, false) }
                )
            }

            DictionaryMode.BIDIRECTIONAL_QUIZ -> {
                val pool = remember(selectedLessonFilter) { buildReviewQueue(selectedLessonFilter) }
                BidirectionalVocabQuiz(
                    pool = pool,
                    onPlayWord = onPlayWord,
                    onRecordResult = onRecordWordReview
                )
            }
        }
    }
}

@Composable
private fun SmartFlashcardDeck(
    words: List<VocabWord>,
    onPlayWord: (String) -> Unit,
    onMarkKnown: (VocabWord) -> Unit,
    onMarkForgotten: (VocabWord) -> Unit
) {
    if (words.isEmpty()) return
    var index by remember(words) { mutableIntStateOf(0) }
    var revealed by remember(index, words) { mutableStateOf(false) }
    var askPersianToEnglish by remember { mutableStateOf(false) }

    val word = words[index % words.size]

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "کارت ${(index % words.size) + 1} از ${words.size} (اولویت هوشمند با لغات نیازمند مرور)",
                style = MaterialTheme.typography.labelLarge,
                color = TextSecondaryMuted
            )
            TvSecondaryButton(
                text = if (askPersianToEnglish) "حالت: فارسی ← انگلیسی" else "حالت: انگلیسی ← فارسی",
                icon = Icons.Default.SwapHoriz,
                onClick = {
                    askPersianToEnglish = !askPersianToEnglish
                    revealed = false
                }
            )
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = DeepSlateSurface),
            shape = RoundedCornerShape(22.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "درس ${word.lessonId} • ${word.categoryFa}",
                    style = MaterialTheme.typography.labelLarge,
                    color = AmberGoldSecondary
                )

                if (!askPersianToEnglish) {
                    LtrEnglishText(
                        text = word.english,
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontFamily = EnglishDisplayFontFamily,
                            color = SkyTealPrimary
                        )
                    )
                    Text(text = word.phonetic, style = MaterialTheme.typography.titleMedium, color = TextTertiarySubtle)
                    TvSecondaryButton(
                        text = "شنیدن تلفظ انگلیسی",
                        icon = Icons.AutoMirrored.Filled.VolumeUp,
                        onClick = { onPlayWord(word.english) }
                    )
                } else {
                    Text(
                        text = word.persian,
                        style = MaterialTheme.typography.displaySmall,
                        color = SkyTealPrimary
                    )
                }

                if (revealed) {
                    HorizontalDivider(color = SubtleOutline)
                    if (!askPersianToEnglish) {
                        Text(
                            text = word.persian,
                            style = MaterialTheme.typography.headlineMedium,
                            color = EmeraldMintTertiary
                        )
                    } else {
                        LtrEnglishText(
                            text = "${word.english} (${word.phonetic})",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = EnglishDisplayFontFamily,
                                color = EmeraldMintTertiary
                            )
                        )
                        TvSecondaryButton(
                            text = "شنیدن تلفظ",
                            icon = Icons.AutoMirrored.Filled.VolumeUp,
                            onClick = { onPlayWord(word.english) }
                        )
                    }
                    LtrEnglishText(text = word.exampleEn)
                    Text(text = word.exampleFa, style = MaterialTheme.typography.bodyMedium, color = TextSecondaryMuted)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (!revealed) {
                TvActionButton(
                    text = "نمایش معنی و مثال",
                    icon = Icons.Default.Visibility,
                    onClick = { revealed = true },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("flashcard_reveal")
                )
            } else {
                TvActionButton(
                    text = "فراموش کرده بودم (تکرار شود)",
                    icon = Icons.Default.Replay,
                    containerColor = CoralError,
                    onClick = {
                        onMarkForgotten(word)
                        index++
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("flashcard_forgot")
                )
                TvActionButton(
                    text = "کاملاً بلدم ✓",
                    icon = Icons.Default.Check,
                    containerColor = EmeraldMintTertiary,
                    onClick = {
                        onMarkKnown(word)
                        index++
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("flashcard_known")
                )
            }
        }
    }
}

@Composable
private fun BidirectionalVocabQuiz(
    pool: List<VocabWord>,
    onPlayWord: (String) -> Unit,
    onRecordResult: (VocabWord, Boolean) -> Unit
) {
    if (pool.size < 4) return
    var questionCounter by remember(pool) { mutableIntStateOf(0) }
    val targetWord = remember(questionCounter, pool) { pool[questionCounter % pool.size] }
    val askEnglishToPersian = remember(questionCounter) { questionCounter % 2 == 0 }

    val options = remember(targetWord, askEnglishToPersian) {
        val distractors = Prospect2Catalog.allWords
            .filter { it.id != targetWord.id }
            .shuffled()
            .take(3)
        (distractors + targetWord).shuffled()
    }

    var selectedId by remember(questionCounter, pool) { mutableStateOf<String?>(null) }

    Card(
        colors = CardDefaults.cardColors(containerColor = DeepSlateSurface),
        shape = RoundedCornerShape(22.dp),
        modifier = Modifier.fillMaxWidth()
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
                    text = if (askEnglishToPersian) "پرسش انگلیسی ← انتخاب معنی فارسی" else "پرسش فارسی ← انتخاب کلمه انگلیسی",
                    style = MaterialTheme.typography.titleMedium,
                    color = AmberGoldSecondary
                )
                TvSecondaryButton(
                    text = "تلفظ کلمه",
                    icon = Icons.AutoMirrored.Filled.VolumeUp,
                    onClick = { onPlayWord(targetWord.english) }
                )
            }

            if (askEnglishToPersian) {
                LtrEnglishText(
                    text = targetWord.english,
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontFamily = EnglishDisplayFontFamily,
                        color = SkyTealPrimary
                    )
                )
            } else {
                Text(
                    text = targetWord.persian,
                    style = MaterialTheme.typography.displaySmall,
                    color = SkyTealPrimary
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                options.forEach { optWord ->
                    val picked = selectedId == optWord.id
                    val isRight = optWord.id == targetWord.id
                    val bg = when {
                        selectedId == null -> ElevatedCardSurface
                        isRight -> EmeraldMintContainer
                        picked -> CoralErrorContainer
                        else -> ElevatedCardSurface
                    }
                    TvFocusableCard(
                        onClick = {
                            if (selectedId == null) {
                                selectedId = optWord.id
                                onRecordResult(targetWord, isRight)
                            }
                        },
                        accentColor = if (selectedId != null && isRight) EmeraldMintTertiary else SkyTealPrimary,
                        containerColor = bg
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (askEnglishToPersian) {
                                Text(text = optWord.persian, style = MaterialTheme.typography.bodyLarge, color = TextPrimaryLight)
                            } else {
                                LtrEnglishText(text = optWord.english)
                            }
                            if (selectedId != null && (isRight || picked)) {
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

            if (selectedId != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (selectedId == targetWord.id) "✅ عالی بود! امتیاز و تسلط افزایش یافت." else "❌ پاسخ درست: ${targetWord.english} = ${targetWord.persian} (برای مرور مجدد ثبت شد)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimaryLight,
                        modifier = Modifier.weight(1f)
                    )
                    TvActionButton(
                        text = "کلمه بعدی",
                        onClick = { questionCounter++ },
                        modifier = Modifier.testTag("vocab_quiz_next")
                    )
                }
            }
        }
    }
}
