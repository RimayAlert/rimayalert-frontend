package com.fazq.rimayalert.features.auth.views.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.preferences.UserPreferencesManager
import com.fazq.rimayalert.core.services.FCMManager
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.states.DialogState
import com.fazq.rimayalert.features.auth.domain.usecase.RegisterUseCase
import com.fazq.rimayalert.features.auth.views.event.RegisterEvent
import com.fazq.rimayalert.features.auth.views.state.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterUserViewModel @Inject constructor(
    private val useCase: RegisterUseCase,
    private val userPreferencesManager: UserPreferencesManager,
    private val fcmManager: FCMManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    init {
        obtainFCMToken()
        loadSavedLocation()
    }

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnRegisterDataChange -> {
                _uiState.update {
                    it.copy(
                        registerData = event.registerData,
                        displayNameError = false,
                        emailError = false,
                        passwordError = false,
                        confirmPasswordError = false
                    )
                }
            }

            is RegisterEvent.OnAcceptTermsChange -> {
                _uiState.update {
                    it.copy(
                        registerData = it.registerData.copy(acceptTerms = event.accept),
                        termsError = false
                    )
                }
            }

            RegisterEvent.OnRegisterClick -> registerUser()
            RegisterEvent.OnDismissDialog -> dismissDialog()
            RegisterEvent.OnRetryObtainToken -> obtainFCMToken()
        }
    }

    private fun loadSavedLocation() {
        viewModelScope.launch {
            userPreferencesManager.location.firstOrNull()?.let { (lat, lon) ->
                if (lat != null && lon != null) {
                    _uiState.update {
                        it.copy(
                            registerData = it.registerData.copy(
                                latitude = lat,
                                longitude = lon
                            )
                        )
                    }
                    Log.d("RegisterViewModel", "Ubicación cargada: $lat, $lon")
                } else {
                    Log.w("RegisterViewModel", "No hay ubicación guardada")
                }
            }
        }
    }


    private fun obtainFCMToken() {
        viewModelScope.launch {
            try {
                val token = fcmManager.getToken()
                val deviceId = fcmManager.getDeviceName()

                _uiState.update {
                    it.copy(
                        registerData = it.registerData.copy(
                            fcmToken = token ?: "",
                            deviceId = deviceId
                        )
                    )
                }

                Log.d("RegisterViewModel", "Token FCM obtenido: $token")
            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Error obteniendo token FCM: ${e.message}")
            }
        }
    }

    private fun registerUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            when (val result = useCase.registerUser(_uiState.value.registerData)) {
                is DataState.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            dialogState = DialogState.Success(
                                title = "¡Registro exitoso!",
                                message = "Tu cuenta ha sido creada correctamente."
                            )
                        )
                    }
                }

                is DataState.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            dialogState = DialogState.Error(
                                title = "Error en el registro",
                                message = result.message
                            )
                        )
                    }
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        val data = _uiState.value.registerData
        var isValid = true

        if (data.username.isBlank()) {
            _uiState.update { it.copy(displayNameError = true) }
            isValid = false
        }

        if (data.email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(data.email)
                .matches()
        ) {
            _uiState.update { it.copy(emailError = true) }
            isValid = false
        }

        if (data.password.length < 6) {
            _uiState.update { it.copy(passwordError = true) }
            isValid = false
        }

        if (data.password != data.confirmPassword) {
            _uiState.update { it.copy(confirmPasswordError = true) }
            isValid = false
        }

        if (!data.acceptTerms) {
            _uiState.update { it.copy(termsError = true) }
            isValid = false
        }

        return isValid
    }

    private fun dismissDialog() {
        _uiState.update { it.copy(dialogState = DialogState.None) }
    }
}