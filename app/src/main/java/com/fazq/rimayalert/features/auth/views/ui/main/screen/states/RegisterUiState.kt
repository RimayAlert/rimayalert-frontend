package com.fazq.rimayalert.features.auth.views.ui.main.screen.states

data class RegisterUiState(
    val displayName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val acceptTerms: Boolean = false,
    val acceptPrivacy: Boolean = false,
    val showPassword: Boolean = false,
    val showConfirmPassword: Boolean = false,

    val displayNameError: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val confirmPasswordError: Boolean = false,
    val termsError: Boolean = false
)