package com.fazq.rimayalert.features.auth.views.event


import com.fazq.rimayalert.features.auth.domain.model.RegisterUserModel

sealed class RegisterEvent {
    data class OnRegisterDataChange(val registerData: RegisterUserModel) : RegisterEvent()
    data class OnFieldTouched(val field: RegisterField) : RegisterEvent()
    data class OnAcceptTermsChange(val accept: Boolean) : RegisterEvent()
    data object OnRegisterClick : RegisterEvent()
    data object OnDismissDialog : RegisterEvent()
    data object OnRetryObtainToken : RegisterEvent()
}

enum class RegisterField {
    LAST_NAME,
    FIRST_NAME,
    DISPLAY_NAME,
    CEDULA,
    EMAIL,
    TELEFONO,
    PASSWORD,
    CONFIRM_PASSWORD
}