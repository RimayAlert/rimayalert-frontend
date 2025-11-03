package com.fazq.rimayalert.features.auth.ui.screens

import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.compose.rememberNavController
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.core.ui.theme.AuthColors
import com.fazq.rimayalert.core.ui.theme.Dimensions
import com.fazq.rimayalert.features.auth.domain.model.RegisterUserModel
import com.fazq.rimayalert.features.auth.ui.components.AuthButton
import com.fazq.rimayalert.features.auth.ui.components.AuthFooterText
import com.fazq.rimayalert.core.ui.components.AuthTopBar
import com.fazq.rimayalert.features.auth.ui.components.RegisterCheckboxes
import com.fazq.rimayalert.features.auth.ui.components.RegisterFormFields
import com.fazq.rimayalert.features.auth.ui.viewmodel.RegisterUserViewModel


@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    registerUserViewModel: RegisterUserViewModel = hiltViewModel()
) {
    var registerState by remember { mutableStateOf(RegisterUserModel()) }
    var displayNameError by remember { mutableStateOf(false) }

    val registerUserUiState by registerUserViewModel.registerUserUiState.collectAsState()

    LaunchedEffect(registerUserUiState) {
        when (registerUserUiState) {
            is BaseUiState.SuccessState<*> -> {
                onRegisterSuccess()
            }

            is BaseUiState.ErrorState -> {
                val error = (registerUserUiState as BaseUiState.ErrorState).message
                Log.d("RegisterScreen", "Registration error: $error")
            }

            else -> {}
        }
    }

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
                    .padding(horizontal = Dimensions.spacingMedium)
                    .padding(top = Dimensions.spacingMedium, bottom = Dimensions.spacingXXLarge),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    shadowElevation = Dimensions.spacingSmall
                ) {
                    Column(
                        modifier = Modifier.padding(Dimensions.spacingMedium),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RegisterFormFields(
                            registerData = registerState,
                            onDataChange = {
                                registerState = it
                                displayNameError = it.displayName.isNotBlank()
                            },
                            displayNameError = displayNameError
                        )

                        Spacer(modifier = Modifier.height(Dimensions.spacingMedium))

                        RegisterCheckboxes(
                            acceptTerms = registerState.acceptTerms,
                            onAcceptTermsChange = {
                                registerState = registerState.copy(acceptTerms = it)
                            },
                            onTermsClick = onTermsClick
                        )

                        Spacer(modifier = Modifier.height(Dimensions.spacingXLarge))

                        AuthButton(
                            text = "Crear Cuenta",
                            onClick = {
                                registerUserViewModel.registerUser(registerState)
                            },
                            enabled = registerState.username.isNotBlank() &&
                                    registerState.email.isNotBlank() &&
                                    registerState.displayName.isNotBlank() &&
                                    registerState.password.isNotBlank() &&
                                    registerState.confirmPassword.isNotBlank()
                        )

                        if (registerUserUiState is BaseUiState.LoadingState) {
                            CircularProgressIndicator(
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        AuthFooterText(
                            normalText = "¿Ya tienes una cuenta? ",
                            clickableText = "Inicia sesión",
                            onClick = onLoginClick
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
    val navController = rememberNavController()
    RegisterScreen(
        onRegisterSuccess = {},
        onLoginClick = {},
        onBackClick = {},
        onTermsClick = {},
    )
}