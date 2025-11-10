package com.fazq.rimayalert.core.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.Dimensions
import com.fazq.rimayalert.core.ui.theme.FontWeights
import com.fazq.rimayalert.core.ui.theme.TextSizes


@Composable
fun NavigationItemComponent(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor =
        if (isSelected) AppColors.primary.copy(alpha = 0.1f) else Color.Transparent
    val iconColor = if (isSelected) AppColors.primary else AppColors.textSecondary
    val textColor = if (isSelected) AppColors.textPrimary else AppColors.textSecondary

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(Dimensions.cornerRadiusExtraLarge))
            .background(backgroundColor)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(
                horizontal = Dimensions.paddingDefault,
                vertical = Dimensions.paddingCompact
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = iconColor,
            modifier = Modifier.size(Dimensions.iconSizeLarge)
        )

        Text(
            text = label,
            fontSize = TextSizes.small,
            fontWeight = if (isSelected) FontWeights.medium else FontWeights.normal,
            color = textColor
        )
    }
}