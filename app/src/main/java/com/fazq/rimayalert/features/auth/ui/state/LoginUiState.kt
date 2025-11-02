package com.fazq.rimayalert.features.auth.ui.state

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val showWelcomeMessage: Boolean = false
)