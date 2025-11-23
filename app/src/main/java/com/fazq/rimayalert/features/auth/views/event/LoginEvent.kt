package com.fazq.rimayalert.features.auth.views.event

sealed class LoginEvent {
    data class UsernameChanged(val username: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data object LoginButtonClicked : LoginEvent()
    object ClearErrorMessage : LoginEvent()
    object ClearSuccessMessage : LoginEvent()
    data class SaveLocation(val latitude: Double, val longitude: Double) : LoginEvent()
    object PermissionGranted : LoginEvent()
    object PermissionDenied : LoginEvent()
    object PermissionRequestAttempt : LoginEvent()
    data class FieldError(val field: String, val message: String) : LoginEvent()
    data class GeneralError(val message: String) : LoginEvent()
}