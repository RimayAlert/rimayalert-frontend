package com.fazq.rimayalert.features.auth.views.state

import com.fazq.rimayalert.core.states.DialogState
import com.fazq.rimayalert.features.auth.domain.model.RegisterUserModel

data class RegisterUiState(
    val registerData: RegisterUserModel = RegisterUserModel(),

    val displayNameError: String? = null,
    val cedulaError: String? = null,
    val emailError: String? = null,
    val telefonoError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val termsError: Boolean = false,

    val displayNameTouched: Boolean = false,
    val cedulaTouched: Boolean = false,
    val emailTouched: Boolean = false,
    val telefonoTouched: Boolean = false,
    val passwordTouched: Boolean = false,
    val confirmPasswordTouched: Boolean = false,

    val isLoading: Boolean = false,
    val fcmToken: String? = null,
    val deviceId: String = "",

    val dialogState: DialogState = DialogState.None
)