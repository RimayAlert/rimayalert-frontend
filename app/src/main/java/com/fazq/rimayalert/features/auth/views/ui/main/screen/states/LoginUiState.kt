package com.fazq.rimayalert.features.auth.views.ui.main.screen.states

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val showWelcomeMessage: Boolean = false
)