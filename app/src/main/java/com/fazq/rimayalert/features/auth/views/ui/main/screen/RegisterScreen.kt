package com.fazq.rimayalert.features.auth.views.ui.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fazq.rimayalert.core.ui.theme.AuthColors
import com.fazq.rimayalert.features.auth.domain.model.RegisterFormData
import com.fazq.rimayalert.features.auth.views.ui.main.components.AuthButton
import com.fazq.rimayalert.features.auth.views.ui.main.components.AuthFooterText
import com.fazq.rimayalert.features.auth.views.ui.main.components.AuthTopBar
import com.fazq.rimayalert.features.auth.views.ui.main.components.RegisterCheckboxes
import com.fazq.rimayalert.features.auth.views.ui.main.components.RegisterFormFields



@Composable
fun RegisterScreen(
    onRegisterClick: (RegisterFormData) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onTermsClick: () -> Unit = {}
) {
    var formData by remember { mutableStateOf(RegisterFormData()) }
    var displayNameError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthColors.Background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AuthTopBar(
                title = "Crear cuenta",
                onBackClick = onBackClick
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(top = 24.dp, bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    shadowElevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RegisterFormFields(
                            username = formData.username,
                            onUsernameChange = { formData = formData.copy(username = it) },
                            email = formData.email,
                            onEmailChange = { formData = formData.copy(email = it) },
                            displayName = formData.displayName,
                            onDisplayNameChange = {
                                formData = formData.copy(displayName = it)
                                displayNameError = it.isNotBlank()
                            },
                            phone = formData.phone,
                            onPhoneChange = { formData = formData.copy(phone = it) },
                            password = formData.password,
                            onPasswordChange = { formData = formData.copy(password = it) },
                            confirmPassword = formData.confirmPassword,
                            onConfirmPasswordChange = { formData = formData.copy(confirmPassword = it) },
                            displayNameError = displayNameError
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        RegisterCheckboxes(
                            acceptTerms = formData.acceptTerms,
                            onAcceptTermsChange = { formData = formData.copy(acceptTerms = it) },
                            acceptNotifications = formData.acceptNotifications,
                            onAcceptNotificationsChange = { formData = formData.copy(acceptNotifications = it) },
                            onTermsClick = onTermsClick
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        AuthButton(
                            text = "Crear Cuenta",
                            onClick = { onRegisterClick(formData) },
                            enabled = formData.username.isNotBlank() &&
                                    formData.email.isNotBlank() &&
                                    formData.displayName.isNotBlank() &&
                                    formData.password.isNotBlank() &&
                                    formData.confirmPassword.isNotBlank() &&
                                    formData.acceptTerms
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        AuthFooterText(
                            normalText = "¿Ya tienes una cuenta? ",
                            linkText = "Inicia sesión",
                            onLinkClick = onLoginClick
                        )
                    }
                }
            }
        }
    }
}