package com.fazq.rimayalert.features.auth.domain.model

data class RegisterUserModel(
    val username: String = "",
    val dni : String = "",
    val firstName : String = "",
    val lastName : String = "",
    val email: String = "",
    val displayName: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val acceptTerms: Boolean = false,
)

