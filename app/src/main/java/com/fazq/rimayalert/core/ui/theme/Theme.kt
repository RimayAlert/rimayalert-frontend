package com.fazq.rimayalert.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = SurfaceLight,
    primaryContainer = PrimaryVariant,
    secondary = Secondary,
    background = BackgroundLight,
    surface = SurfaceLight,
    error = ErrorColor,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun RimayAlertTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}