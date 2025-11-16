package com.fazq.rimayalert.features.auth.views.state

import com.fazq.rimayalert.core.states.DialogState
import com.fazq.rimayalert.features.auth.domain.model.RegisterUserModel

data class RegisterUiState(
    val registerData: RegisterUserModel = RegisterUserModel(),

    val showPassword: Boolean = false,
    val showConfirmPassword: Boolean = false,

    val displayNameError: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val confirmPasswordError: Boolean = false,
    val termsError: Boolean = false,

    val isLoading: Boolean = false,
    val fcmToken: String? = null,
    val deviceId: String = "",

    val dialogState: DialogState = DialogState.None
)