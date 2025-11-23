package com.fazq.rimayalert.features.auth.views.event

sealed class LoginEvent {
    data class UsernameChanged(val username: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class RememberMeChanged(val value: Boolean) : LoginEvent()
    data object LoginButtonClicked : LoginEvent()
    object ClearErrorMessage : LoginEvent()
    object ClearSuccessMessage : LoginEvent()
    data class SaveLocation(val latitude: Double, val longitude: Double) : LoginEvent()
    object PermissionGranted : LoginEvent()
    object PermissionDenied : LoginEvent()
    object PermissionRequestAttempt : LoginEvent()
}