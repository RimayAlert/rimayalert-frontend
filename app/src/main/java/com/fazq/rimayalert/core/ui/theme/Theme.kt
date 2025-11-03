package com.fazq.rimayalert.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = AppColors.primary,
    onPrimary = AppColors.surfaceLight,
    primaryContainer = AppColors.primaryVariant,
    secondary = AppColors.secondary,
    background = AppColors.backgroundLight,
    surface = AppColors.surfaceLight,
    error = AppColors.errorColor,
    onBackground = AppColors.textPrimary,
    onSurface = AppColors.textPrimary
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