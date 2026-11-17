package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val ProspectDarkColorScheme = darkColorScheme(
    primary = SkyTealPrimary,
    onPrimary = MidnightBg,
    primaryContainer = SkyTealContainer,
    onPrimaryContainer = TextPrimaryLight,
    secondary = AmberGoldSecondary,
    onSecondary = MidnightBg,
    secondaryContainer = AmberGoldContainer,
    onSecondaryContainer = TextPrimaryLight,
    tertiary = EmeraldMintTertiary,
    onTertiary = MidnightBg,
    tertiaryContainer = EmeraldMintContainer,
    onTertiaryContainer = TextPrimaryLight,
    background = MidnightBg,
    onBackground = TextPrimaryLight,
    surface = DeepSlateSurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = ElevatedCardSurface,
    onSurfaceVariant = TextSecondaryMuted,
    outline = SubtleOutline,
    error = CoralError,
    onError = MidnightBg,
    errorContainer = CoralErrorContainer,
    onErrorContainer = TextPrimaryLight
)

val ProspectShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun ProspectTutorTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ProspectDarkColorScheme,
        typography = ProspectTypography,
        shapes = ProspectShapes,
        content = content
    )
}
