package com.fazq.rimayalert.features.auth.views.components.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.fazq.rimayalert.core.ui.theme.Dimensions
import com.fazq.rimayalert.features.auth.domain.model.RegisterUserModel
import com.fazq.rimayalert.features.auth.views.event.RegisterField

@Composable
fun RegisterFormFieldsComponent(
    registerData: RegisterUserModel,
    onDataChange: (RegisterUserModel) -> Unit,
    onFieldTouched: (RegisterField) -> Unit,
    displayNameError: String?,
    cedulaError: String?,
    emailError: String?,
    telefonoError: String?,
    passwordError: String?,
    confirmPasswordError: String?
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimensions.gapMedium)
    ) {
        ModernTextField(
            value = registerData.displayName,
            onValueChange = { onDataChange(registerData.copy(displayName = it)) },
            onFocusLost = { onFieldTouched(RegisterField.DISPLAY_NAME) },
            label = "Nombre completo",
            error = displayNameError,
            leadingIcon = Icons.Default.Person
        )

        ModernTextField(
            value = registerData.dni,
            onValueChange = { newValue ->
                val filtered = newValue.filter { it.isDigit() }.take(10)
                onDataChange(registerData.copy(dni = filtered))
            },
            onFocusLost = { onFieldTouched(RegisterField.CEDULA) },
            label = "Cédula",
            error = cedulaError,
            keyboardType = KeyboardType.Number,
            leadingIcon = Icons.Default.Badge
        )

        ModernTextField(
            value = registerData.email,
            onValueChange = { onDataChange(registerData.copy(email = it)) },
            onFocusLost = { onFieldTouched(RegisterField.EMAIL) },
            label = "Correo electrónico",
            error = emailError,
            keyboardType = KeyboardType.Email,
            leadingIcon = Icons.Default.Email
        )

        ModernTextField(
            value = registerData.phone,
            onValueChange = { newValue ->
                val filtered = newValue.filter { it.isDigit() }.take(10)
                onDataChange(registerData.copy(phone = filtered))
            },
            onFocusLost = { onFieldTouched(RegisterField.TELEFONO) },
            label = "Teléfono",
            error = telefonoError,
            keyboardType = KeyboardType.Phone,
            leadingIcon = Icons.Default.Phone
        )

        ModernTextField(
            value = registerData.username,
            onValueChange = { onDataChange(registerData.copy(username = it)) },
            label = "Nombre de usuario",
            leadingIcon = Icons.Default.AccountCircle
        )

        ModernTextField(
            value = registerData.password,
            onValueChange = { onDataChange(registerData.copy(password = it)) },
            onFocusLost = { onFieldTouched(RegisterField.PASSWORD) },
            label = "Contraseña",
            error = passwordError,
            isPassword = true,
            keyboardType = KeyboardType.Password,
            leadingIcon = Icons.Default.Lock
        )

        ModernTextField(
            value = registerData.confirmPassword,
            onValueChange = { onDataChange(registerData.copy(confirmPassword = it)) },
            onFocusLost = { onFieldTouched(RegisterField.CONFIRM_PASSWORD) },
            label = "Confirmar contraseña",
            error = confirmPasswordError,
            isPassword = true,
            keyboardType = KeyboardType.Password,
            leadingIcon = Icons.Default.Lock
        )
    }
}

@Composable
fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    onFocusLost: (() -> Unit)? = null,
    error: String? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    leadingIcon: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = if (error != null) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                }
            },
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        )
                    }
                }
            } else null,
            isError = error != null,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    onFocusLost?.invoke()
                    focusManager.moveFocus(FocusDirection.Down)
                },
                onDone = {
                    onFocusLost?.invoke()
                    focusManager.clearFocus()
                }
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color(0xFFE0E0E0),
                errorBorderColor = MaterialTheme.colorScheme.error,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = Color(0xFF757575),
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = Color(0xFFF8F9FA),
                unfocusedContainerColor = Color(0xFFFAFAFA),
                errorContainerColor = Color(0xFFFFF5F5)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (isFocused && !focusState.isFocused) {
                        onFocusLost?.invoke()
                    }
                    isFocused = focusState.isFocused
                }
        )

        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}