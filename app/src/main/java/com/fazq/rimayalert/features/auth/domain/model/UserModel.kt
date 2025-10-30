package com.fazq.rimayalert.features.auth.domain.model

import com.fazq.rimayalert.features.auth.domain.entities.UserEntity

data class User(
    val id: Int,
    val username: String?,
    val dni: String?,
    val fullName: String?,
    val tokenNotification: String?,
    val phone: String,
    val email: String,
    val active: Boolean = false,
)

fun UserEntity.toDomain() = User(
    id = id,
    username = username,
    dni = null,
    fullName = null,
    tokenNotification = null,
    phone = "",
    email = "",
    active = false

)