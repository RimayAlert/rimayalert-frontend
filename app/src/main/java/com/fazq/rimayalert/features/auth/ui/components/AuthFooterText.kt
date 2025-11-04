package com.fazq.rimayalert.features.auth.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.Dimensions
import com.fazq.rimayalert.core.ui.theme.FontWeights
import com.fazq.rimayalert.core.ui.theme.TextSizes

@Composable
fun AuthFooterText(
    normalText: String,
    clickableText: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = normalText,
            fontSize = TextSizes.medium,
            color = AppColors.secondary
        )
        TextButton(
            onClick = onClick,
            contentPadding = PaddingValues(Dimensions.paddingNone),
            enabled = enabled
        ) {
            Text(
                text = clickableText,
                fontSize = TextSizes.medium,
                color = AppColors.primary,
                fontWeight = FontWeights.medium
            )
        }
    }
}