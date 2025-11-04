package com.fazq.rimayalert.features.auth.ui.screens

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.features.auth.domain.model.AuthModel
import com.fazq.rimayalert.features.auth.ui.components.LoginContentComponent
import com.fazq.rimayalert.features.auth.ui.state.LoginUiState
import com.fazq.rimayalert.features.auth.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authUiState by authViewModel.authUiState.collectAsState()
    var localUiState by remember { mutableStateOf(LoginUiState()) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(authUiState) {
        when (val state = authUiState) {
            is BaseUiState.SuccessState<*> -> {
                val message = (state.data as? String) ?: "¡Inicio de sesión exitoso!"
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Short
                )
                onLoginSuccess()
                authViewModel.resetState()
            }

            is BaseUiState.ErrorState -> {

                authViewModel.resetState()
            }

            else -> {}
        }
    }
    LoginContentComponent(
        uiState = localUiState,
        authState = authUiState,
        snackbarHostState = snackbarHostState,
        onUserNameChange = { localUiState = localUiState.copy(userName = it) },
        onPasswordChange = { localUiState = localUiState.copy(password = it) },
        onRememberMeChange = { localUiState = localUiState.copy(rememberMe = it) },
        onLoginClick = {
            authViewModel.auth(
                AuthModel(
                    username = "dev-test",
                    password = "devtest"
                )
            )
        },
        onRegisterClick = onRegisterClick,
        onForgotPasswordClick = onForgotPasswordClick
    )
}

