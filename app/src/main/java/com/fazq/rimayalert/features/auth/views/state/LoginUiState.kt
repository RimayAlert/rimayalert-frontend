package com.fazq.rimayalert.features.auth.views.state

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val loginSuccess: Boolean = false
)