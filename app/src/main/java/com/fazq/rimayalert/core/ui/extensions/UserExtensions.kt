package com.fazq.rimayalert.core.ui.extensions

import com.fazq.rimayalert.features.auth.domain.model.UserModel

fun UserModel?.getDisplayName(): String {
    return this?.let { user ->
        user.aliasName?.takeIf { it.isNotEmpty() }
            ?: user.fullName?.takeIf { it.isNotEmpty() }
            ?: user.username
            ?: "Usuario"
    } ?: "Usuario"
}