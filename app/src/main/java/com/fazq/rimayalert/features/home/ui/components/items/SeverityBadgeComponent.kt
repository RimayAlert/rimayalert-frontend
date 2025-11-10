package com.fazq.rimayalert.features.home.ui.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.Dimensions


@Composable
fun SeverityBadgeComponent(severity: String) {
    val (backgroundColor, textColor, text) = when (severity.lowercase()) {
        "high", "alta", "crítica", "critical" -> Triple(
            AppColors.errorColor.copy(alpha = 0.1f),
            AppColors.errorColor,
            "Alta"
        )

        "medium", "media", "moderate" -> Triple(
            AppColors.warningBackground,
            AppColors.warningIcon,
            "Media"
        )

        else -> Triple(
            AppColors.successBackground,
            AppColors.successIcon,
            "Baja"
        )
    }

    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(Dimensions.cornerRadiusSmall))
            .padding(
                horizontal = Dimensions.paddingCompact,
                vertical = Dimensions.paddingTiny
            )
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}