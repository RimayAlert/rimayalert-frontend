package com.fazq.rimayalert.features.home.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class ActivityStyleModel (
    val icon: ImageVector,
    val bgColor: androidx.compose.ui.graphics.Color,
    val iconColor: androidx.compose.ui.graphics.Color,
    val statusBgColor: androidx.compose.ui.graphics.Color,
    val statusTextColor: androidx.compose.ui.graphics.Color
)
