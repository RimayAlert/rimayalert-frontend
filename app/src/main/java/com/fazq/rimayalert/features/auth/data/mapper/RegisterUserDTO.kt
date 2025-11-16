package com.fazq.rimayalert.features.auth.data.mapper

import com.google.gson.annotations.SerializedName

data class RegisterUserDTO(
    @SerializedName("username")
    val username: String,
    @SerializedName("dni")
    val dni: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("displayName")
    val displayName: String,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("password")
    val password: String,
    @SerializedName("fcmToken")
    val fcmToken: String?,
    @SerializedName("deviceId")
    val deviceId: String?
)