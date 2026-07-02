package com.example.citiesapplication.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val textPrimary: Color,
    val textButton: Color,
    val subtitleErrorText: Color,
    val shimmerBackground: Color,
    val textTertiary: Color,
    val backgroundTertiary: Color,
    val accentPrimary: Color,
    val buttonPressed: Color,
    val dividerPrimary: Color,
    val pinBackground: Color
)

private val LightAppColors = AppColors(
    textPrimary = TextPrimary,
    textButton = TextButton,
    subtitleErrorText = SubtitleErrorText,
    shimmerBackground = ShimmerBackground,
    textTertiary = TextTertiary,
    backgroundTertiary = BackgroundTertiary,
    accentPrimary = AccentPrimary,
    buttonPressed = ButtonPressed,
    dividerPrimary = DividerPrimary,
    pinBackground = PinBackground
)

private val LocalAppColors = staticCompositionLocalOf { LightAppColors }

internal val LocalTypography = staticCompositionLocalOf<AppTypography> {
    error("No typography provided")
}

@Composable
fun CitiesApplicationTheme(
    content: @Composable () -> Unit
) {
    val colors = LightAppColors
    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalTypography provides getAppTypography(),
    ) {
        MaterialTheme(
            content = content
        )
    }
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        get() = LocalTypography.current
}