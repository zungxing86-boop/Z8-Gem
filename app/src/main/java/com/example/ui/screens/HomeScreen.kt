package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.curriculum.Prospect2Catalog
import com.example.data.model.CurriculumUnit
import com.example.data.model.UnitType
import com.example.ui.components.LtrEnglishText
import com.example.ui.components.SectionHeaderBadge
import com.example.ui.components.TvActionButton
import com.example.ui.components.TvFocusableCard
import com.example.ui.components.TvSecondaryButton
import com.example.ui.theme.*
import com.example.ui.viewmodel.TutorDashboardState

@Composable
fun HomeScreen(
    dashboardState: TutorDashboardState,
    isWideScreen: Boolean,
    onOpenUnit: (unitId: Int, startStage: Int) -> Unit,
    onOpenWeakWordsReview: () -> Unit,
    onOpenAiTutor: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lastUnit = Prospect2Catalog.unitById(dashboardState.preferences.lastUnitId)
    val lastStage = dashboardState.preferences.lastStageIndex
    val columns = if (isWideScreen) 2 else 1

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = if (isWideScreen) 28.dp else 16.dp,
            vertical = 20.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Banner
        item(span = { GridItemSpan(columns) }) {
            HeroTutorBanner(
                studentName = dashboardState.preferences.studentName,
                lastUnit = lastUnit,
                lastStage = lastStage,
                isWideScreen = isWideScreen,
                onContinueClick = { onOpenUnit(lastUnit.id, lastStage) },
                onAskTutorClick = onOpenAiTutor
            )
        }

        // KPI / Quick Status Row
        item(span = { GridItemSpan(columns) }) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatChipCard(
                    title = "پیشرفت کتاب",
                    value = "${dashboardState.overallBookPercent}٪",
                    icon = Icons.Default.AutoGraph,
                    accent = SkyTealPrimary,
                    modifier = Modifier.weight(1f),
                    onClick = { onOpenUnit(lastUnit.id, lastStage) }
                )
                StatChipCard(
                    title = "امتیاز یادگیری",
                    value = "${dashboardState.preferences.totalXp} XP",
                    icon = Icons.Default.EmojiEvents,
                    accent = AmberGoldSecondary,
                    modifier = Modifier.weight(1f),
                    onClick = { onOpenUnit(lastUnit.id, lastStage) }
                )
                StatChipCard(
                    title = "واژگان تثبیت‌شده",
                    value = "${dashboardState.masteredWordsCount} / ${Prospect2Catalog.allWords.size}",
                    icon = Icons.Default.Verified,
                    accent = EmeraldMintTertiary,
                    modifier = Modifier.weight(1f),
                    onClick = onOpenWeakWordsReview
                )
                if (isWideScreen) {
                    StatChipCard(
                        title = "نیازمند مرور",
                        value = "${dashboardState.weakWords.size} لغت",
                        icon = Icons.Default.ReplayCircleFilled,
                        accent = if (dashboardState.weakWords.isNotEmpty()) CoralError else EmeraldMintTertiary,
                        modifier = Modifier.weight(1f),
                        onClick = onOpenWeakWordsReview
                    )
                }
            }
        }

        // Section Header
        item(span = { GridItemSpan(columns) }) {
            SectionHeaderBadge(
                title = "مسیر گام‌به‌گام درس‌های کتاب (Prospect 2)",
                subtitle = "۷ درس اصلی + ۳ مرور جامع مطابق ترتیب و ساختار کتاب درسی پایه هشتم",
                accentColor = SkyTealPrimary
            )
        }

        // Curriculum Units in exact book order
        items(Prospect2Catalog.units, key = { it.id }) { unit ->
            val progress = dashboardState.lessonProgressMap[unit.id]?.completionPercent ?: 0
            val bestQuiz = dashboardState.lessonProgressMap[unit.id]?.let { p ->
                if (p.quizTotalQuestions > 0) "${p.quizBestScore}/${p.quizTotalQuestions}" else null
            }
            UnitRoadmapCard(
                unit = unit,
                completionPercent = progress,
                bestQuizLabel = bestQuiz,
                isLastVisited = unit.id == lastUnit.id,
                onClick = { onOpenUnit(unit.id, 0) }
            )
        }
    }
}

@Composable
private fun HeroTutorBanner(
    studentName: String,
    lastUnit: CurriculumUnit,
    lastStage: Int,
    isWideScreen: Boolean,
    onContinueClick: () -> Unit,
    onAskTutorClick: () -> Unit
) {
    val stageLabels = listOf("مکالمه", "واژگان", "گرامر", "حل تمرین", "آزمون")
    val stageName = stageLabels.getOrElse(lastStage) { "مکالمه" }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(DeepSlateSurface)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_tv_banner_1794882547276),
            contentDescription = "تصویر پس‌زمینه معلم هوشمند پراسپکت ۲",
            modifier = Modifier
                .matchParentSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.34f
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MidnightBg.copy(alpha = 0.94f),
                            MidnightBg.copy(alpha = 0.78f),
                            DeepSlateSurface.copy(alpha = 0.55f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (isWideScreen) 28.dp else 18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = SkyTealContainer
                ) {
                    Text(
                        text = "پایه هشتم متوسطه اول • Prospect 2",
                        style = MaterialTheme.typography.labelMedium,
                        color = SkyTealPrimary,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
                Surface(
                    shape = RoundedCornerShape(50),
                    color = EmeraldMintContainer
                ) {
                    Text(
                        text = "آفلاین + معلم هوشمند",
                        style = MaterialTheme.typography.labelMedium,
                        color = EmeraldMintTertiary,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Text(
                text = "خوش آمدی، $studentName!",
                style = MaterialTheme.typography.displaySmall,
                color = TextPrimaryLight
            )
            Text(
                text = "معلم خصوصی تعاملی زبان انگلیسی — آماده اجرا با ریموت کنترل تلویزیون، کیبورد و لمس",
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondaryMuted
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TvActionButton(
                    text = "ادامه: ${lastUnit.numberLabel} ($stageName)",
                    icon = Icons.AutoMirrored.Filled.ArrowForward,
                    onClick = onContinueClick,
                    modifier = Modifier.testTag("continue_learning_button")
                )
                TvSecondaryButton(
                    text = "پرسش از معلم هوشمند",
                    icon = Icons.Default.Psychology,
                    accentColor = AmberGoldSecondary,
                    onClick = onAskTutorClick,
                    modifier = Modifier.testTag("hero_ask_tutor_button")
                )
            }
        }
    }
}

@Composable
private fun StatChipCard(
    title: String,
    value: String,
    icon: ImageVector,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TvFocusableCard(
        onClick = onClick,
        accentColor = accent,
        containerColor = DeepSlateSurface,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = accent.copy(alpha = 0.16f),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = accent,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimaryLight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    color = TextTertiarySubtle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun UnitRoadmapCard(
    unit: CurriculumUnit,
    completionPercent: Int,
    bestQuizLabel: String?,
    isLastVisited: Boolean,
    onClick: () -> Unit
) {
    TvFocusableCard(
        onClick = onClick,
        accentColor = unit.accentColor,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("unit_card_${unit.id}")
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = unit.accentColor.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = unit.numberLabel,
                            style = MaterialTheme.typography.labelLarge,
                            color = unit.accentColor,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                    if (unit.type == UnitType.REVIEW) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = AmberGoldContainer
                        ) {
                            Text(
                                text = "ارزیابی دوره‌ای",
                                style = MaterialTheme.typography.labelMedium,
                                color = AmberGoldSecondary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    if (isLastVisited) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = EmeraldMintContainer
                        ) {
                            Text(
                                text = "آخرین مرحله",
                                style = MaterialTheme.typography.labelMedium,
                                color = EmeraldMintTertiary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
                Text(
                    text = unit.pageRange,
                    style = MaterialTheme.typography.labelMedium,
                    color = TextTertiarySubtle
                )
            }

            LtrEnglishText(
                text = unit.titleEn,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = EnglishDisplayFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = TextPrimaryLight
                )
            )
            Text(
                text = "${unit.titleFa} — ${unit.themeFa}",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondaryMuted,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LinearProgressIndicator(
                    progress = { (completionPercent / 100f).coerceIn(0f, 1f) },
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = unit.accentColor,
                    trackColor = SubtleOutline
                )
                Text(
                    text = "$completionPercent٪",
                    style = MaterialTheme.typography.labelLarge,
                    color = unit.accentColor
                )
                if (bestQuizLabel != null) {
                    Text(
                        text = "آزمون: $bestQuizLabel",
                        style = MaterialTheme.typography.labelMedium,
                        color = AmberGoldSecondary
                    )
                }
            }
        }
    }
}
