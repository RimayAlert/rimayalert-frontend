package com.fazq.rimayalert.features.auth.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.Dimensions

@Composable
fun AuthButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(Dimensions.buttonHeightDefault),
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.primary,
            contentColor = AppColors.surfaceLight,
            disabledContainerColor = AppColors.primary.copy(alpha = 0.5f),
            disabledContentColor = AppColors.surfaceLight.copy(alpha = 0.7f)
        ),
        shape = RoundedCornerShape(Dimensions.cornerRadiusSmall),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = Dimensions.elevationNone,
            pressedElevation = Dimensions.elevationLow,
            disabledElevation = Dimensions.elevationNone
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(Dimensions.iconSizeMedium),
                color = AppColors.surfaceLight,
                strokeWidth = Dimensions.strokeWidthThin
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}