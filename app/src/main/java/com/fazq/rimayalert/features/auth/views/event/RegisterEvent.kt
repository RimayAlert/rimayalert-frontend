package com.fazq.rimayalert.features.auth.views.event


import com.fazq.rimayalert.features.auth.domain.model.RegisterUserModel

sealed class RegisterEvent {
    data class OnRegisterDataChange(val registerData: RegisterUserModel) : RegisterEvent()

    data class OnAcceptTermsChange(val accept: Boolean) : RegisterEvent()

    data object OnRegisterClick : RegisterEvent()

    data object OnDismissDialog : RegisterEvent()
    data object OnRetryObtainToken : RegisterEvent()
}