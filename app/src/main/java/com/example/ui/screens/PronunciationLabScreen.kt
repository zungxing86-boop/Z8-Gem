package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.audio.PlaybackState
import com.example.audio.PronunciationResult
import com.example.data.curriculum.Prospect2Catalog
import com.example.data.model.ConversationLine
import com.example.data.model.UnitType
import com.example.ui.components.*
import com.example.ui.theme.*

private enum class LabTab(val titleFa: String) {
    PHONICS("دونویسه‌ها و تلفظ (Phonics)"),
    SYNCED_READER("خواندن همزمان متن کامل"),
    SPEAKING_COACH("تمرین تکرار و تلفظ دانش‌آموز")
}

@Composable
fun PronunciationLabScreen(
    playbackState: PlaybackState,
    pronunciationResult: PronunciationResult,
    isWideScreen: Boolean,
    isSpeechRecAvailable: Boolean,
    onPlayText: (String, String, Int) -> Unit,
    onPlayConversation: (List<ConversationLine>, Int) -> Unit,
    onStopSpeech: () -> Unit,
    onSetSpeechRate: (Float) -> Unit,
    onStartSpeakingPractice: (String) -> Unit,
    onClearSpeakingResult: () -> Unit,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableStateOf(LabTab.PHONICS) }
    val lessons = remember { Prospect2Catalog.units.filter { it.type == UnitType.LESSON } }
    var selectedLessonId by remember { mutableIntStateOf(1) }
    val selectedUnit = remember(selectedLessonId) { Prospect2Catalog.unitById(selectedLessonId) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = if (isWideScreen) 28.dp else 14.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SectionHeaderBadge(
                title = "آزمایشگاه تلفظ و مهارت خواندن",
                subtitle = "پخش همزمان با هایلایت کلمات، آموزش صوتی دوحرفی‌ها و تمرین تکرار جمله",
                accentColor = AmberGoldSecondary,
                modifier = Modifier.weight(1f)
            )
            if (isWideScreen) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(0.75f to "۰.۷۵x آهسته", 0.95f to "۱x عادی", 1.2f to "۱.۲x سریع").forEach { (rate, lbl) ->
                        val active = kotlin.math.abs(playbackState.speechRate - rate) < 0.1f
                        TvSecondaryButton(
                            text = lbl,
                            accentColor = if (active) AmberGoldSecondary else TextTertiarySubtle,
                            onClick = { onSetSpeechRate(rate) }
                        )
                    }
                }
            }
        }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(LabTab.entries) { tab ->
                val selected = activeTab == tab
                TvFocusableCard(
                    onClick = {
                        onStopSpeech()
                        activeTab = tab
                    },
                    accentColor = AmberGoldSecondary,
                    containerColor = if (selected) AmberGoldContainer else DeepSlateSurface,
                    cornerRadius = 12.dp
                ) {
                    Text(
                        text = tab.titleFa,
                        style = MaterialTheme.typography.labelLarge,
                        color = if (selected) TextPrimaryLight else TextSecondaryMuted,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                }
            }
        }

        // Lesson selector chips
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(lessons, key = { it.id }) { unit ->
                val selected = selectedLessonId == unit.id
                TvFocusableCard(
                    onClick = {
                        onStopSpeech()
                        selectedLessonId = unit.id
                    },
                    accentColor = unit.accentColor,
                    containerColor = if (selected) unit.accentColor.copy(alpha = 0.25f) else DeepSlateSurface,
                    cornerRadius = 10.dp
                ) {
                    Text(
                        text = "${unit.numberLabel}: ${unit.titleEn}",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextPrimaryLight,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }

        when (activeTab) {
            LabTab.PHONICS -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(selectedUnit.phonicsSpotlights, key = { it.id }) { spot ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = DeepSlateSurface),
                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                LtrEnglishText(
                                    text = "${spot.pattern}  (${spot.ipaHint})",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontFamily = EnglishDisplayFontFamily,
                                        color = AmberGoldSecondary
                                    )
                                )
                                Text(text = spot.explanationFa, style = MaterialTheme.typography.bodyLarge, color = TextPrimaryLight)
                                Text(text = "⚠️ ${spot.mistakeWarningFa}", style = MaterialTheme.typography.bodyMedium, color = CoralError)

                                spot.sampleWords.forEachIndexed { idx, w ->
                                    TvFocusableCard(
                                        onClick = { onPlayText(w.word, "lab_ph_${spot.id}_$idx", -1) },
                                        accentColor = AmberGoldSecondary
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(12.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                                LtrEnglishText(text = w.word)
                                                Text(text = w.phonetic, style = MaterialTheme.typography.labelMedium, color = SkyTealPrimary)
                                                Text(text = w.persian, style = MaterialTheme.typography.bodyMedium, color = TextSecondaryMuted)
                                            }
                                            Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "شنیدن تلفظ", tint = AmberGoldSecondary)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            LabTab.SYNCED_READER -> {
                val mainConv = selectedUnit.conversations.firstOrNull()
                if (mainConv != null) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${mainConv.titleFa} — پخش کامل با مشخص‌شدن همزمان کلمات",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimaryLight
                                )
                                TvActionButton(
                                    text = if (playbackState.isPlaying) "توقف خواندن" else "پخش کامل متن درس",
                                    icon = if (playbackState.isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                                    containerColor = if (playbackState.isPlaying) CoralError else EmeraldMintTertiary,
                                    onClick = {
                                        if (playbackState.isPlaying) onStopSpeech() else onPlayConversation(mainConv.lines, 0)
                                    }
                                )
                            }
                        }
                        itemsIndexed(mainConv.lines) { idx, line ->
                            val isActive = playbackState.isPlaying && playbackState.activeLineIndex == idx
                            TvFocusableCard(
                                onClick = { onPlayText(line.english, "lab_line_$idx", idx) },
                                accentColor = AmberGoldSecondary,
                                containerColor = if (isActive) HighlightCardSurface else ElevatedCardSurface
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(text = line.speaker, style = MaterialTheme.typography.labelLarge, color = selectedUnit.accentColor)
                                    SyncedSpeechLine(
                                        text = line.english,
                                        isLineActive = isActive,
                                        activeWordStart = playbackState.activeWordStart,
                                        activeWordEnd = playbackState.activeWordEnd,
                                        fontSizeSp = 21
                                    )
                                    Text(text = line.persian, style = MaterialTheme.typography.bodyMedium, color = TextTertiarySubtle)
                                }
                            }
                        }
                    }
                }
            }

            LabTab.SPEAKING_COACH -> {
                SpeakingCoachView(
                    targetSentences = selectedUnit.keyExpressions.map { it.english to it.persian } +
                        (selectedUnit.conversations.firstOrNull()?.lines?.take(3)?.map { it.english to it.persian } ?: emptyList()),
                    pronunciationResult = pronunciationResult,
                    isSpeechRecAvailable = isSpeechRecAvailable,
                    onListenModel = { en -> onPlayText(en, "coach_model", -1) },
                    onStartPractice = onStartSpeakingPractice,
                    onClearResult = onClearSpeakingResult
                )
            }
        }
    }
}

@Composable
private fun SpeakingCoachView(
    targetSentences: List<Pair<String, String>>,
    pronunciationResult: PronunciationResult,
    isSpeechRecAvailable: Boolean,
    onListenModel: (String) -> Unit,
    onStartPractice: (String) -> Unit,
    onClearResult: () -> Unit
) {
    val context = LocalContext.current
    var sentenceIdx by remember(targetSentences) { mutableIntStateOf(0) }
    var permissionDeniedNote by remember { mutableStateOf(false) }

    val activePair = targetSentences.getOrNull(sentenceIdx) ?: return

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            permissionDeniedNote = false
            onStartPractice(activePair.first)
        } else {
            permissionDeniedNote = true
        }
    }

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
                    Text(
                        text = "تمرین تکرار جمله (${sentenceIdx + 1} از ${targetSentences.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = AmberGoldSecondary
                    )
                    LtrEnglishText(
                        text = activePair.first,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontFamily = EnglishDisplayFontFamily,
                            color = TextPrimaryLight
                        )
                    )
                    Text(text = activePair.second, style = MaterialTheme.typography.bodyLarge, color = TextSecondaryMuted)

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        TvActionButton(
                            text = "۱. اول گوش کن",
                            icon = Icons.AutoMirrored.Filled.VolumeUp,
                            onClick = { onListenModel(activePair.first) },
                            modifier = Modifier.testTag("coach_listen_button")
                        )

                        TvActionButton(
                            text = if (pronunciationResult.isListening) "در حال گوش دادن..." else "۲. حالا تکرار کن (میکروفون)",
                            icon = Icons.Default.Mic,
                            containerColor = if (pronunciationResult.isListening) CoralError else EmeraldMintTertiary,
                            onClick = {
                                val hasPerm = ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.RECORD_AUDIO
                                ) == PackageManager.PERMISSION_GRANTED
                                if (hasPerm) {
                                    onStartPractice(activePair.first)
                                } else {
                                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                }
                            },
                            modifier = Modifier.testTag("coach_mic_button")
                        )

                        TvSecondaryButton(
                            text = "جمله بعدی",
                            onClick = {
                                onClearResult()
                                sentenceIdx = (sentenceIdx + 1) % targetSentences.size
                            }
                        )
                    }

                    if (!isSpeechRecAvailable || permissionDeniedNote) {
                        Surface(shape = RoundedCornerShape(12.dp), color = AmberGoldContainer.copy(alpha = 0.65f)) {
                            Text(
                                text = "ℹ️ اگر تلویزیون یا دستگاه شما میکروفون فعال ندارد، روی «۱. اول گوش کن» بزنید و بلافاصله بعد از گوینده با صدای بلند تکرار کنید تا لهجه و ریتم جمله تثبیت شود.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimaryLight,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    if (pronunciationResult.scorePercent != null) {
                        val score = pronunciationResult.scorePercent
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (score >= 70) EmeraldMintContainer else AmberGoldContainer
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = "امتیاز تلفظ شما: $score٪",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = if (score >= 70) EmeraldMintTertiary else AmberGoldSecondary
                                )
                                LtrEnglishText(text = "Heard: \"${pronunciationResult.recognizedText}\"")
                                Text(text = pronunciationResult.feedbackFa, style = MaterialTheme.typography.bodyLarge, color = TextPrimaryLight)
                            }
                        }
                    } else if (!pronunciationResult.errorMessage.isNullOrBlank()) {
                        Surface(shape = RoundedCornerShape(12.dp), color = CoralErrorContainer) {
                            Text(
                                text = pronunciationResult.errorMessage,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimaryLight,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
