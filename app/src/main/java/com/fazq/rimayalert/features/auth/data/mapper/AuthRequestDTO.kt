package com.fazq.rimayalert.features.auth.data.mapper

import com.fazq.rimayalert.features.auth.domain.model.AuthModel


data class AuthRequestDTO(
    val username: String,
    val password: String,
)

fun AuthModel.toModel() = AuthRequestDTO(username, password)

