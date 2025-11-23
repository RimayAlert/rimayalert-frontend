package com.fazq.rimayalert.features.auth.views.state

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val loginSuccess: Boolean = false,
    val hasLocationPermission: Boolean = false,
    val hasAskedPermissionBefore: Boolean = false,
    val usernameError: String? = null,
    val passwordError: String? = null
)