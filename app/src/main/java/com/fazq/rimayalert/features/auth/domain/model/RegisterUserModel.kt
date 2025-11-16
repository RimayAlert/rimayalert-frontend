package com.fazq.rimayalert.features.auth.domain.model

import com.fazq.rimayalert.features.auth.data.mapper.RegisterUserDTO

data class RegisterUserModel(
    val username: String = "",
    val dni: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val displayName: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val acceptTerms: Boolean = false,
    val fcmToken: String = "",
    val deviceId: String = ""
)

fun RegisterUserModel.toDTO(): RegisterUserDTO {
    return RegisterUserDTO(
        username,
        dni,
        firstName,
        lastName,
        email,
        displayName,
        phone.ifBlank { null },
        password,
        fcmToken.ifBlank { null },
        deviceId.ifBlank { null }
    )
}