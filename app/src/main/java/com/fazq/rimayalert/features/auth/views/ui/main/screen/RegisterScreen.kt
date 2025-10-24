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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fazq.rimayalert.core.ui.theme.AuthColors
import com.fazq.rimayalert.features.auth.domain.model.RegisterModel
import com.fazq.rimayalert.features.auth.views.ui.main.components.AuthButton
import com.fazq.rimayalert.features.auth.views.ui.main.components.AuthFooterText
import com.fazq.rimayalert.features.auth.views.ui.main.components.AuthTopBar
import com.fazq.rimayalert.features.auth.views.ui.main.components.RegisterCheckboxes
import com.fazq.rimayalert.features.auth.views.ui.main.components.RegisterFormFields
import com.fazq.rimayalert.features.auth.views.viewmodel.RegisterViewModel


@Composable
fun RegisterScreen(
    onRegisterClick: (RegisterModel) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    registerViewModel : RegisterViewModel = hiltViewModel()
) {
    var registerState by remember { mutableStateOf(RegisterModel()) }
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
                            first_name = registerState.first_name,
                            onFirstNameChange = {
                                registerState = registerState.copy(first_name = it)
                            },
                            last_name = registerState.last_name,
                            onLastNameChange = {
                                registerState = registerState.copy(last_name = it)
                            },
                            dni = registerState.dni,
                            onDniChange = {
                                registerState = registerState.copy(dni = it)
                            },
                            username = registerState.username,
                            onUsernameChange = {
                                registerState = registerState.copy(username = it)
                            },
                            email = registerState.email,
                            onEmailChange = { registerState = registerState.copy(email = it) },
                            displayName = registerState.displayName,
                            onDisplayNameChange = {
                                registerState = registerState.copy(displayName = it)
                                displayNameError = it.isNotBlank()
                            },
                            phone = registerState.phone,
                            onPhoneChange = { registerState = registerState.copy(phone = it) },
                            password = registerState.password,
                            onPasswordChange = {
                                registerState = registerState.copy(password = it)
                            },
                            confirmPassword = registerState.confirmPassword,
                            onConfirmPasswordChange = {
                                registerState = registerState.copy(confirmPassword = it)
                            },
                            displayNameError = displayNameError,
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        RegisterCheckboxes(
                            acceptTerms = registerState.acceptTerms,
                            onAcceptTermsChange = { registerState = registerState.copy(acceptTerms = it) },
                            onTermsClick = onTermsClick
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        AuthButton(
                            text = "Crear Cuenta",
                            onClick = { onRegisterClick(registerState) },
                            enabled = registerState.username.isNotBlank() &&
                                    registerState.email.isNotBlank() &&
                                    registerState.displayName.isNotBlank() &&
                                    registerState.password.isNotBlank() &&
                                    registerState.confirmPassword.isNotBlank()
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


@Preview(showBackground = true, showSystemUi = false)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        onRegisterClick = {},
        onLoginClick = {},
        onBackClick = {},
    )
}