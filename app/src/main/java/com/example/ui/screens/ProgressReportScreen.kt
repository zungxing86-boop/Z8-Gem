package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.curriculum.Prospect2Catalog
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.TutorDashboardState

@Composable
fun ProgressReportScreen(
    dashboardState: TutorDashboardState,
    isWideScreen: Boolean,
    onUpdateStudentName: (String) -> Unit,
    onOpenLesson: (Int) -> Unit,
    onPracticeWeakWords: () -> Unit,
    onAskTutorWeakness: () -> Unit,
    onResetAllProgress: () -> Unit,
    modifier: Modifier = Modifier
) {
    var editingName by remember(dashboardState.preferences.studentName) {
        mutableStateOf(dashboardState.preferences.studentName)
    }
    var showResetDialog by remember { mutableStateOf(false) }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("بازنشانی پیشرفت دانش‌آموز") },
            text = { Text("آیا مطمئن هستید که می‌خواهید تمام امتیازها، تاریخچه لغات و پیشرفت دروس را از دستگاه پاک کنید؟") },
            confirmButton = {
                TvActionButton(
                    text = "بله، پاک شود",
                    containerColor = CoralError,
                    onClick = {
                        onResetAllProgress()
                        showResetDialog = false
                    }
                )
            },
            dismissButton = {
                TvSecondaryButton(
                    text = "انصراف",
                    onClick = { showResetDialog = false }
                )
            }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = if (isWideScreen) 28.dp else 14.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 28.dp)
    ) {
        item {
            SectionHeaderBadge(
                title = "گزارش پیشرفت، نقاط ضعف و مشخصات فنی",
                subtitle = "ذخیره‌سازی ۱۰۰٪ محلی روی دستگاه (Room + DataStore) برای پیگیری تسلط دانش‌آموز",
                accentColor = EmeraldMintTertiary
            )
        }

        // Student Profile & Summary Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DeepSlateSurface),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = editingName,
                            onValueChange = { editingName = it },
                            label = { Text("نام دانش‌آموز") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        TvActionButton(
                            text = "ذخیره نام",
                            icon = Icons.Default.Save,
                            onClick = { onUpdateStudentName(editingName) }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        SummaryMetricBox("پیشرفت کل کتاب", "${dashboardState.overallBookPercent}٪", SkyTealPrimary, Modifier.weight(1f))
                        SummaryMetricBox("امتیاز کل", "${dashboardState.preferences.totalXp} XP", AmberGoldSecondary, Modifier.weight(1f))
                        SummaryMetricBox("لغات تثبیت‌شده", "${dashboardState.masteredWordsCount}/${Prospect2Catalog.allWords.size}", EmeraldMintTertiary, Modifier.weight(1f))
                        SummaryMetricBox("لغات نیازمند مرور", "${dashboardState.weakWords.size}", CoralError, Modifier.weight(1f))
                    }
                }
            }
        }

        // Weaknesses & Actionable Recommendations
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DeepSlateSurface),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "تحلیل نقاط ضعف و پیشنهاد تمرین بیشتر",
                        style = MaterialTheme.typography.titleLarge,
                        color = AmberGoldSecondary
                    )
                    if (dashboardState.weakWords.isEmpty() && dashboardState.recentMistakes.isEmpty()) {
                        Text(
                            text = "هنوز نقطه ضعفی ثبت نشده است! پس از انجام آزمون‌ها و تمرین‌های واژگان، مباحثی که نیاز به تکرار دارند اینجا نمایش داده می‌شوند.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextSecondaryMuted
                        )
                    } else {
                        if (dashboardState.weakWords.isNotEmpty()) {
                            Text(
                                text = "واژگان فراموش‌شده برای مرور هوشمند: " +
                                    dashboardState.weakWords.take(8).joinToString("، ") { "${it.english} (${it.persian})" },
                                style = MaterialTheme.typography.bodyLarge,
                                color = CoralError
                            )
                        }
                        if (dashboardState.recentMistakes.isNotEmpty()) {
                            Text("آخرین خطاهای ثبت‌شده در گرامر و تمرین:", style = MaterialTheme.typography.titleMedium, color = TextPrimaryLight)
                            dashboardState.recentMistakes.take(5).forEach { m ->
                                Surface(shape = RoundedCornerShape(10.dp), color = ElevatedCardSurface) {
                                    Text(
                                        text = "• [درس ${m.lessonId} - ${m.topicTag}] ${m.itemTitle} ← پاسخ درست: ${m.correctAnswer}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextSecondaryMuted,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            }
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        TvActionButton(
                            text = "تمرین لغات فراموش‌شده",
                            icon = Icons.Default.Replay,
                            containerColor = CoralError,
                            onClick = onPracticeWeakWords
                        )
                        TvSecondaryButton(
                            text = "آموزش جبرانی با معلم هوشمند",
                            icon = Icons.Default.Psychology,
                            accentColor = AmberGoldSecondary,
                            onClick = onAskTutorWeakness
                        )
                    }
                }
            }
        }

        // Per-Lesson Progress Breakdown
        item {
            Text(
                text = "گزارش تسلط به تفکیک درس‌ها و مرورها",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimaryLight
            )
        }

        items(Prospect2Catalog.units, key = { it.id }) { unit ->
            val progress = dashboardState.lessonProgressMap[unit.id]
            val percent = progress?.completionPercent ?: 0
            TvFocusableCard(
                onClick = { onOpenLesson(unit.id) },
                accentColor = unit.accentColor
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${unit.numberLabel}: ${unit.titleFa} (${unit.titleEn})",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimaryLight
                        )
                        val quizInfo = if ((progress?.quizAttempts ?: 0) > 0) {
                            "بهترین نمره آزمون: ${progress?.quizBestScore}/${progress?.quizTotalQuestions}"
                        } else {
                            "هنوز آزمون پایان درس داده نشده"
                        }
                        Text(text = quizInfo, style = MaterialTheme.typography.bodyMedium, color = TextTertiarySubtle)
                    }
                    LinearProgressIndicator(
                        progress = { percent / 100f },
                        modifier = Modifier
                            .width(120.dp)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = unit.accentColor,
                        trackColor = SubtleOutline
                    )
                    Text(text = "$percent٪", style = MaterialTheme.typography.labelLarge, color = unit.accentColor)
                }
            }
        }

        // Audience, TV Guide & Technical Output Overview
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = DeepSlateSurface),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "مشخصات آموزشی، مخاطب و راهنمای تلویزیون (Android TV)",
                        style = MaterialTheme.typography.titleLarge,
                        color = SkyTealPrimary
                    )
                    Text(
                        text = "• مخاطب و پایه تحصیلی: دانش‌آموزان پایه هشتم دوره اول متوسطه (کتاب انگلیسی ۲ — Prospect 2، کد ۸۱۰)، والدین و دبیران جهت تمرین خانگی روی تلویزیون یا تبلت.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryMuted
                    )
                    Text(
                        text = "• کار با کنترل تلویزیون (D-Pad): با کلیدهای جهت‌نما (بالا، پایین، چپ، راست) بین کارت‌ها و منوها حرکت کنید، با کلید OK/Enter انتخاب کنید و با Back بازگردید.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryMuted
                    )
                    Text(
                        text = "• معماری آفلاین + هوشمند: تمام متن‌ها، واژگان، گرامر، تمرین‌ها، صوت تلفظ (TextToSpeech) و پایگاه داده پیشرفت (Room + DataStore) به‌صورت کامل روی دستگاه و بدون نیاز به اینترنت کار می‌کنند و در صورت اتصال اینترنت از معلم هوشمند ابری نیز بهره‌مند می‌شوید.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryMuted
                    )

                    Spacer(Modifier.height(6.dp))
                    TvSecondaryButton(
                        text = "پاک‌کردن و شروع مجدد پیشرفت دانش‌آموز",
                        icon = Icons.Default.DeleteForever,
                        accentColor = CoralError,
                        onClick = { showResetDialog = true },
                        modifier = Modifier.testTag("reset_progress_button")
                    )
                }
            }
        }
    }
}

@Composable
private fun SummaryMetricBox(
    label: String,
    value: String,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = ElevatedCardSurface,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = value, style = MaterialTheme.typography.titleLarge, color = color)
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = TextSecondaryMuted)
        }
    }
}
