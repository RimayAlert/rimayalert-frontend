package com.fazq.rimayalert.features.auth.data.mapper

data class RegisterUserDTO (
    val username: String,
    val dni : String,
    val firstName : String,
    val lastName : String,
    val email: String,
    val displayName: String,
    val phone: String,
    val password: String
)