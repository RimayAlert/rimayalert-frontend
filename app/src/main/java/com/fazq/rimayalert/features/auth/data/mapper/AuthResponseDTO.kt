package com.fazq.rimayalert.features.auth.data.mapper

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDTO(
    @SerialName("message")
    val message: String,
    @SerialName("token")
    val token: String,
    @SerialName("user")
    val user: UserDataDTO
)

@Serializable
data class UserDataDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("username")
    val username: String,
    @SerialName("email")
    val email: String,
    @SerialName("dni")
    val dni: String,
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("alias_name")
    val aliasName: String?,
    @SerialName("is_active")
    val isActive: Boolean
)