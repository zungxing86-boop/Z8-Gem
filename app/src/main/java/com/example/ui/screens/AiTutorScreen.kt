package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ai.TutorStreamState
import com.example.data.curriculum.Prospect2Catalog
import com.example.data.model.CurriculumUnit
import com.example.data.model.UnitType
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.ChatMessage

@Composable
fun AiTutorScreen(
    messages: List<ChatMessage>,
    streamState: TutorStreamState,
    selectedUnit: CurriculumUnit,
    initialPrompt: String?,
    isWideScreen: Boolean,
    onSelectUnit: (Int) -> Unit,
    onSendPrompt: (question: String, unit: CurriculumUnit, forceOffline: Boolean) -> Unit,
    onClearChat: () -> Unit,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val lessons = remember { Prospect2Catalog.units.filter { it.type == UnitType.LESSON } }

    LaunchedEffect(initialPrompt) {
        if (!initialPrompt.isNullOrBlank()) {
            onSendPrompt(initialPrompt, selectedUnit, false)
        }
    }

    LaunchedEffect(messages.size, streamState) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    val quickPrompts = remember(selectedUnit.id) {
        listOf(
            "نقاط ضعف من را تحلیل کن و تمرین جبرانی بده",
            "گرامر ${selectedUnit.numberLabel} (${selectedUnit.titleFa}) را خیلی ساده توضیح بده",
            "۳ مثال جدید خارج از کتاب برای ${selectedUnit.numberLabel} بزن",
            "یک سؤال تمرینی با راهنمای گام‌به‌گام از ${selectedUnit.numberLabel} بپرس"
        )
    }

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
                title = "معلم هوشمند و تعاملی (AI Tutor)",
                subtitle = "پاسخ‌گویی به سؤالات، مثال‌های بیشتر خارج از کتاب و آموزش مجدد نقاط ضعف",
                accentColor = AmberGoldSecondary,
                modifier = Modifier.weight(1f)
            )
            TvSecondaryButton(
                text = "شروع گفتگوی جدید",
                icon = Icons.Default.Refresh,
                accentColor = CoralError,
                onClick = onClearChat
            )
        }

        // Unit Context Selector
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(lessons, key = { it.id }) { unit ->
                val active = unit.id == selectedUnit.id
                TvFocusableCard(
                    onClick = { onSelectUnit(unit.id) },
                    accentColor = unit.accentColor,
                    containerColor = if (active) unit.accentColor.copy(alpha = 0.24f) else DeepSlateSurface,
                    cornerRadius = 10.dp
                ) {
                    Text(
                        text = "${unit.numberLabel}: ${unit.titleFa}",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextPrimaryLight,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }

        // Quick D-Pad Prompt Chips (Ideal for TV remote without typing!)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(quickPrompts) { prompt ->
                TvFocusableCard(
                    onClick = { onSendPrompt(prompt, selectedUnit, false) },
                    accentColor = AmberGoldSecondary,
                    containerColor = AmberGoldContainer.copy(alpha = 0.45f),
                    cornerRadius = 12.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = AmberGoldSecondary, modifier = Modifier.size(16.dp))
                        Text(text = prompt, style = MaterialTheme.typography.labelMedium, color = TextPrimaryLight)
                    }
                }
            }
        }

        // Chat Feed
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                ChatBubble(message = msg)
            }

            if (streamState is TutorStreamState.Loading) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = DeepSlateSurface),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.5.dp, color = SkyTealPrimary)
                            Text("معلم هوشمند در حال آماده کردن پاسخ آموزشی...", style = MaterialTheme.typography.bodyMedium, color = TextSecondaryMuted)
                        }
                    }
                }
            } else if (streamState is TutorStreamState.Streaming) {
                item {
                    ChatBubble(
                        message = ChatMessage(
                            isStudent = false,
                            text = streamState.text
                        )
                    )
                }
            }
        }

        // Input Row + Offline Fallback Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text("سؤال خود را به فارسی یا انگلیسی بنویسید...") },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .testTag("ai_tutor_input"),
                shape = RoundedCornerShape(14.dp)
            )
            TvActionButton(
                text = "ارسال به معلم",
                icon = Icons.AutoMirrored.Filled.Send,
                onClick = {
                    val q = inputText.trim()
                    if (q.isNotEmpty()) {
                        onSendPrompt(q, selectedUnit, false)
                        inputText = ""
                    }
                },
                modifier = Modifier.testTag("ai_tutor_send_button")
            )
            TvSecondaryButton(
                text = "پاسخ آفلاین",
                icon = Icons.Default.WifiOff,
                accentColor = EmeraldMintTertiary,
                onClick = {
                    val q = inputText.ifBlank { "نکات مهم و مثال‌های اضافه این درس را بگو" }
                    onSendPrompt(q, selectedUnit, true)
                    inputText = ""
                },
                modifier = Modifier.testTag("ai_tutor_offline_button")
            )
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage) {
    val alignment = if (message.isStudent) Alignment.CenterEnd else Alignment.CenterStart
    val containerColor = when {
        message.isStudent -> SkyTealContainer
        message.isError -> CoralErrorContainer
        else -> DeepSlateSurface
    }

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = alignment) {
        Surface(
            color = containerColor,
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.widthIn(max = 720.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = if (message.isStudent) Icons.Default.Person else Icons.Default.Psychology,
                        contentDescription = null,
                        tint = if (message.isStudent) SkyTealPrimary else AmberGoldSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = when {
                            message.isStudent -> "شما"
                            message.isOfflineFallback -> "معلم هوشمند (حالت آفلاین)"
                            message.isError -> "وضعیت اتصال معلم"
                            else -> "معلم هوشمند (آنلاین)"
                        },
                        style = MaterialTheme.typography.labelMedium,
                        color = if (message.isStudent) SkyTealPrimary else AmberGoldSecondary
                    )
                }
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextPrimaryLight
                )
            }
        }
    }
}
