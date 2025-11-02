package com.fazq.rimayalert.features.auth.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.fazq.rimayalert.core.ui.theme.AuthColors.BorderColor
import com.fazq.rimayalert.core.ui.theme.AuthColors.ErrorColor
import com.fazq.rimayalert.core.ui.theme.AuthColors.Primary
import com.fazq.rimayalert.core.ui.theme.AuthColors.TextHint
import com.fazq.rimayalert.core.ui.theme.SurfaceLight

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            visualTransformation = when {
                isPassword && passwordVisible -> PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            trailingIcon = {
                if (isPassword) {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible }
                    ) {
//                        Icon(
//                            imageVector = if (passwordVisible)
//                                Icons.Default.Visibility
//                            else
//                                Icons.Default.VisibilityOff,
//                            contentDescription = if (passwordVisible)
//                                "Ocultar contraseña"
//                            else
//                                "Mostrar contraseña",
//                            tint = TextHint
//                        )
                    }
                }
            },
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = BorderColor,
                errorBorderColor = ErrorColor,
                focusedLabelColor = Primary,
                unfocusedLabelColor = TextHint,
                errorLabelColor = ErrorColor,
                cursorColor = Primary,
                focusedContainerColor = SurfaceLight,
                unfocusedContainerColor = SurfaceLight,
                errorContainerColor = SurfaceLight
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )

        if (isError && !errorMessage.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = ErrorColor,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}