package com.fazq.rimayalert.features.auth.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.fazq.rimayalert.core.ui.components.dialogs.ErrorDialogComponent
import com.fazq.rimayalert.core.ui.components.dialogs.SuccessDialogComponent
import com.fazq.rimayalert.features.auth.ui.components.sections.LoginContentComponent
import com.fazq.rimayalert.features.auth.ui.event.LoginEvent
import com.fazq.rimayalert.features.auth.ui.viewmodel.AuthViewModel


@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val loginUiState by authViewModel.uiState.collectAsState()

    LaunchedEffect(loginUiState.loginSuccess) {
        if (loginUiState.loginSuccess) onLoginSuccess()
    }

    loginUiState.successMessage?.let { message ->
        SuccessDialogComponent(
            openDialog = true,
            message = message,
            onDismiss = {
                authViewModel.onEvent(LoginEvent.ClearSuccessMessage)
            }
        )
    }

    loginUiState.errorMessage?.let { message ->
        ErrorDialogComponent(
            openDialog = true,
            message = message,
            onDismiss = {
                authViewModel.onEvent(LoginEvent.ClearErrorMessage)
            }
        )
    }


    LoginContentComponent(
        uiState = loginUiState,
        onUserNameChange = {
            authViewModel.onEvent(LoginEvent.UsernameChanged(it.trim()))
        },
        onPasswordChange = {
            authViewModel.onEvent(LoginEvent.PasswordChanged(it.trim()))
        },
        onRememberMeChange = { },
        onLoginClick = {
            authViewModel.onEvent(LoginEvent.LoginButtonClicked)
        },
        onRegisterClick = onRegisterClick,
        onForgotPasswordClick = onForgotPasswordClick
    )
}

