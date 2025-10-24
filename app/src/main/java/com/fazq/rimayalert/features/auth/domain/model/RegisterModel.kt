package com.fazq.rimayalert.features.auth.domain.model

data class RegisterModel(
    val username: String = "",
    val dni : String = "",
    val first_name : String = "",
    val last_name : String = "",
    val email: String = "",
    val displayName: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val acceptTerms: Boolean = false,
)