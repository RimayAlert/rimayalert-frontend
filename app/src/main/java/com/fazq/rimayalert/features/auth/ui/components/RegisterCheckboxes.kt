package com.fazq.rimayalert.features.auth.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterCheckboxes(
    acceptTerms: Boolean,
    onAcceptTermsChange: (Boolean) -> Unit,
    onTermsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = acceptTerms,
                onCheckedChange = onAcceptTermsChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = AuthColors.Primary,
                    uncheckedColor = AuthColors.BorderColor
                )
            )
            Row(
                modifier = Modifier.padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Acepto los ",
                    fontSize = 14.sp,
                    color = AuthColors.TextSecondary
                )
                TextButton(
                    onClick = onTermsClick,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.height(20.dp)
                ) {
                    Text(
                        text = "términos de uso",
                        fontSize = 14.sp,
                        color = AuthColors.Primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}