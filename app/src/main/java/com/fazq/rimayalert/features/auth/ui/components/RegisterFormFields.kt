package com.fazq.rimayalert.features.auth.ui.components

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
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.TextSizes
import com.fazq.rimayalert.features.auth.domain.model.RegisterUserModel

@Composable
fun RegisterFormFields(
    registerData: RegisterUserModel,
    onDataChange: (RegisterUserModel) -> Unit,
    displayNameError: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        AuthTextField(
            value = registerData.firstName,
            onValueChange = { onDataChange(registerData.copy(firstName = it)) },
            label = "Nombre",
            keyboardType = KeyboardType.Text
        )

        Spacer(modifier = Modifier.height(16.dp))


        AuthTextField(
            value = registerData.lastName,
            onValueChange = { onDataChange(registerData.copy(lastName = it)) },
            label = "apellido",
            keyboardType = KeyboardType.Text
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = registerData.dni,
            onValueChange = { onDataChange(registerData.copy(dni = it)) },
            label = "Dni",
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(16.dp))


        AuthTextField(
            value = registerData.username,
            onValueChange = { onDataChange(registerData.copy(username = it)) },
            label = "Nombre de usuario",
            keyboardType = KeyboardType.Text
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = registerData.email,
            onValueChange = { onDataChange(registerData.copy(email = it)) },
            label = "Correo electrónico",
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = registerData.displayName,
            onValueChange = { onDataChange(registerData.copy(displayName = it)) },
            label = "Nombre público (Alias)",
            keyboardType = KeyboardType.Text,
            isError = displayNameError,
            errorMessage = if (displayNameError)
                "Este nombre se mostrará en tus reportes. No uses tu nombre real."
            else null
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = registerData.phone,
            onValueChange = { onDataChange(registerData.copy(phone = it)) },
            label = "Teléfono (opcional)",
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = registerData.password,
            onValueChange = { onDataChange(registerData.copy(password = it)) },
            label = "Contraseña",
            isPassword = true,
            keyboardType = KeyboardType.Password
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Mínimo 8 caracteres, incluyendo números y símbolos.",
            fontSize = TextSizes.small,
            color = AppColors.textHint,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthTextField(
            value = registerData.confirmPassword,
            onValueChange = { onDataChange(registerData.copy(confirmPassword = it)) },
            label = "Confirmar contraseña",
            isPassword = true,
            keyboardType = KeyboardType.Password
        )
    }
}