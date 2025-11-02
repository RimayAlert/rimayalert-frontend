package com.fazq.rimayalert.features.auth.domain.model

import com.fazq.rimayalert.features.auth.domain.entities.UserEntity

data class User(
    val id: Int,
    val username: String?,
    val firstName : String? = null,
    val lastName : String? = null,
    val dni: String?,
    val fullName: String?,
    val token: String?,
    val phone: String,
    val email: String,
    val aliasName: String? = null,
    val active: Boolean = false,
)

fun UserEntity.toDomain() = User(
    id = id,
    username = username,
    firstName = firstName,
    lastName = lastName,
    dni = null,
    fullName = null,
    token = null,
    phone = "",
    email = "",
    aliasName = aliasName,
    active = active
)