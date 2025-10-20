package com.fazq.rimayalert.features.auth.domain.model

data class RegisterFormData(
    val username: String = "",
    val email: String = "",
    val displayName: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val acceptTerms: Boolean = false,
    val acceptNotifications: Boolean = false
)