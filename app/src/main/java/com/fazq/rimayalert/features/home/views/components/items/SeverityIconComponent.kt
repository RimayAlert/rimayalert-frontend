package com.fazq.rimayalert.features.home.views.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.Dimensions

@Composable
fun SeverityIconComponent(severity: String) {
    val (backgroundColor, iconColor, icon) = when (severity.lowercase()) {
        "high", "alta", "crítica", "critical" -> Triple(
            AppColors.errorColor.copy(alpha = 0.1f),
            AppColors.errorColor,
            Icons.Default.Warning
        )

        "medium", "media", "moderate" -> Triple(
            AppColors.warningIconBackground,
            AppColors.warningIcon,
            Icons.Default.Info
        )

        else -> Triple(
            AppColors.successIconBackground,
            AppColors.successIcon,
            Icons.Default.CheckCircle
        )
    }

    Box(
        modifier = Modifier
            .size(Dimensions.iconSizeLarge + Dimensions.paddingCompact)
            .background(backgroundColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(Dimensions.iconSizeMedium)
        )
    }
}
