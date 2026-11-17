package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

/**
 * TV D-Pad + Touch focusable card with animated scale and luminous border ring.
 */
@Composable
fun TvFocusableCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    accentColor: Color = SkyTealPrimary,
    containerColor: Color = ElevatedCardSurface,
    focusedContainerColor: Color = HighlightCardSurface,
    cornerRadius: Dp = 18.dp,
    enabled: Boolean = true,
    content: @Composable ColumnScope.(isFocused: Boolean) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.03f else 1f,
        animationSpec = spring(dampingRatio = 0.7f, stiffness = 400f),
        label = "tvCardScale"
    )
    val bgColor by animateColorAsState(
        targetValue = if (isFocused) focusedContainerColor else containerColor,
        label = "tvCardBg"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isFocused) accentColor else SubtleOutline.copy(alpha = 0.55f),
        label = "tvCardBorder"
    )

    Surface(
        modifier = modifier
            .scale(scale)
            .minimumInteractiveComponentSize()
            .onFocusChanged { isFocused = it.isFocused }
            .focusable(enabled = enabled)
            .clip(RoundedCornerShape(cornerRadius))
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick),
        shape = RoundedCornerShape(cornerRadius),
        color = bgColor,
        border = BorderStroke(if (isFocused) 2.5.dp else 1.dp, borderColor),
        tonalElevation = if (isFocused) 8.dp else 2.dp
    ) {
        Column(content = { content(isFocused) })
    }
}

@Composable
fun TvActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    containerColor: Color = SkyTealPrimary,
    contentColor: Color = MidnightBg,
    enabled: Boolean = true
) {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.05f else 1f,
        animationSpec = spring(dampingRatio = 0.7f, stiffness = 420f),
        label = "tvButtonScale"
    )

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .scale(scale)
            .defaultMinSize(minWidth = 48.dp, minHeight = 50.dp)
            .onFocusChanged { isFocused = it.isFocused },
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = SubtleOutline,
            disabledContentColor = TextTertiarySubtle
        ),
        border = if (isFocused) BorderStroke(3.dp, TextPrimaryLight) else null,
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp)
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun TvSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    accentColor: Color = SkyTealPrimary,
    enabled: Boolean = true
) {
    var isFocused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.04f else 1f,
        animationSpec = spring(dampingRatio = 0.75f, stiffness = 400f),
        label = "tvSecScale"
    )

    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .scale(scale)
            .defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)
            .onFocusChanged { isFocused = it.isFocused },
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
            width = if (isFocused) 2.5.dp else 1.2.dp,
            color = if (isFocused) accentColor else SubtleOutline
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isFocused) accentColor.copy(alpha = 0.18f) else DeepSlateSurface,
            contentColor = if (isFocused) TextPrimaryLight else accentColor
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(6.dp))
        }
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}

/**
 * Displays English textbook sentences strictly Left-to-Right with standard English font.
 */
@Composable
fun LtrEnglishText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle(
        fontFamily = EnglishBodyFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        color = TextPrimaryLight
    )
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Text(text = text, style = style, modifier = modifier)
    }
}

/**
 * Highlights the currently spoken word inside an English sentence in real time.
 */
@Composable
fun SyncedSpeechLine(
    text: String,
    isLineActive: Boolean,
    activeWordStart: Int,
    activeWordEnd: Int,
    modifier: Modifier = Modifier,
    fontSizeSp: Int = 19
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        val annotated = remember(text, isLineActive, activeWordStart, activeWordEnd) {
            buildAnnotatedString {
                if (!isLineActive || activeWordStart < 0 || activeWordEnd <= activeWordStart || activeWordStart >= text.length) {
                    append(text)
                } else {
                    val safeEnd = activeWordEnd.coerceAtMost(text.length)
                    append(text.substring(0, activeWordStart))
                    withStyle(
                        SpanStyle(
                            background = AmberGoldSecondary,
                            color = MidnightBg,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(text.substring(activeWordStart, safeEnd))
                    }
                    if (safeEnd < text.length) {
                        append(text.substring(safeEnd))
                    }
                }
            }
        }
        Text(
            text = annotated,
            style = TextStyle(
                fontFamily = EnglishBodyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = fontSizeSp.sp,
                lineHeight = (fontSizeSp + 9).sp,
                color = if (isLineActive) TextPrimaryLight else TextSecondaryMuted
            ),
            modifier = modifier
        )
    }
}

@Composable
fun SectionHeaderBadge(
    title: String,
    subtitle: String? = null,
    accentColor: Color = SkyTealPrimary,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(5.dp)
                .height(34.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(accentColor)
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = TextPrimaryLight
            )
            if (!subtitle.isNullOrBlank()) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextTertiarySubtle
                )
            }
        }
    }
}
