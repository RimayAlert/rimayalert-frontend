package com.fazq.rimayalert.features.auth.data.mapper

import com.google.gson.annotations.SerializedName


data class AuthResponseDTO(
    val token: String,
    @SerializedName("user")
    val user: UserDataDTO
)

data class UserDataDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("dni")
    val dni: String,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("alias_name")
    val aliasName: String?,
    @SerializedName("is_active")
    val isActive: Boolean
)