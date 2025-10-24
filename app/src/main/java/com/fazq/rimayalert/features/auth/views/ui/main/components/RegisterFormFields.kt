package com.fazq.rimayalert.features.auth.views.ui.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fazq.rimayalert.core.ui.theme.AuthColors

@Composable
fun RegisterFormFields(
    first_name : String,
    onFirstNameChange: (String) -> Unit,
    last_name : String,
    onLastNameChange: (String) -> Unit,
    dni : String,
    onDniChange: (String) -> Unit,
    username: String,
    onUsernameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    displayName: String,
    onDisplayNameChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    displayNameError: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        AuthTextField(
            value = first_name,
            onValueChange = onFirstNameChange,
            label = "Nombre",
            keyboardType = KeyboardType.Text
        )

        Spacer(modifier = Modifier.height(16.dp))


        AuthTextField(
            value = last_name,
            onValueChange = onLastNameChange,
            label = "apellido",
            keyboardType = KeyboardType.Text
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = dni,
            onValueChange = onDniChange,
            label = "Dni",
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(16.dp))


        AuthTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = "Nombre de usuario",
            keyboardType = KeyboardType.Text
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = email,
            onValueChange = onEmailChange,
            label = "Correo electrónico",
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = displayName,
            onValueChange = onDisplayNameChange,
            label = "Nombre público (Alias)",
            keyboardType = KeyboardType.Text,
            isError = displayNameError,
            errorMessage = if (displayNameError)
                "Este nombre se mostrará en tus reportes. No uses tu nombre real."
            else null
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = phone,
            onValueChange = onPhoneChange,
            label = "Teléfono (opcional)",
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Contraseña",
            isPassword = true,
            keyboardType = KeyboardType.Password
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Mínimo 8 caracteres, incluyendo números y símbolos.",
            fontSize = 12.sp,
            color = AuthColors.TextHint,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = "Confirmar contraseña",
            isPassword = true,
            keyboardType = KeyboardType.Password
        )
    }
}