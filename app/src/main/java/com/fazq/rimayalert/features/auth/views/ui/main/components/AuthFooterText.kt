package com.fazq.rimayalert.features.auth.views.ui.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fazq.rimayalert.core.ui.theme.AuthColors

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
            fontSize = 14.sp,
            color = AuthColors.TextSecondary
        )
        TextButton(
            onClick = onClick,
            contentPadding = PaddingValues(0.dp),
            enabled = enabled
        ) {
            Text(
                text = clickableText,
                fontSize = 14.sp,
                color = AuthColors.Primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}