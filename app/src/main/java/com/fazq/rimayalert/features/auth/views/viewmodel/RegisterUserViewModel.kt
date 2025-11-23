package com.fazq.rimayalert.features.auth.views.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.preferences.UserPreferencesManager
import com.fazq.rimayalert.core.services.FCMManager
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.states.DialogState
import com.fazq.rimayalert.features.auth.domain.model.RegisterUserModel
import com.fazq.rimayalert.features.auth.domain.usecase.RegisterUseCase
import com.fazq.rimayalert.features.auth.views.event.RegisterEvent
import com.fazq.rimayalert.features.auth.views.event.RegisterField
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
        loadSavedLocation()
    }

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnRegisterDataChange -> {
                updateRegisterData(event.registerData)
            }
            is RegisterEvent.OnFieldTouched -> {
                markFieldAsTouched(event.field)
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

    private fun markFieldAsTouched(field: RegisterField) {
        _uiState.update {
            when (field) {
                RegisterField.DISPLAY_NAME -> it.copy(displayNameTouched = true)
                RegisterField.CEDULA -> it.copy(cedulaTouched = true)
                RegisterField.EMAIL -> it.copy(emailTouched = true)
                RegisterField.TELEFONO -> it.copy(telefonoTouched = true)
                RegisterField.PASSWORD -> it.copy(passwordTouched = true)
                RegisterField.CONFIRM_PASSWORD -> it.copy(confirmPasswordTouched = true)
            }
        }
        // Validar solo el campo que fue tocado
        updateRegisterData(_uiState.value.registerData)
    }

    private fun updateRegisterData(data: RegisterUserModel) {
        _uiState.update {
            it.copy(
                registerData = data,
                displayNameError = if (it.displayNameTouched) validateDisplayName(data.displayName) else null,
                cedulaError = if (it.cedulaTouched) validateCedula(data.dni) else null,
                emailError = if (it.emailTouched) validateEmail(data.email) else null,
                telefonoError = if (it.telefonoTouched) validateTelefono(data.phone) else null,
                passwordError = if (it.passwordTouched) validatePassword(data.password) else null,
                confirmPasswordError = if (it.confirmPasswordTouched) validateConfirmPassword(data.password, data.confirmPassword) else null
            )
        }
    }

    private fun validateDisplayName(displayName: String): String? {
        return when {
            displayName.isBlank() -> "El nombre es requerido"
            displayName.length < 3 -> "El nombre debe tener al menos 3 caracteres"
            else -> null
        }
    }

    private fun validateCedula(cedula: String): String? {
        return when {
            cedula.isBlank() -> "La cédula es requerida"
            cedula.length != 10 -> "La cédula debe tener 10 dígitos"
            !cedula.all { it.isDigit() } -> "La cédula solo debe contener números"
            else -> null
        }
    }

    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "El correo es requerido"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Correo inválido"
            else -> null
        }
    }

    private fun validateTelefono(telefono: String): String? {
        return when {
            telefono.isBlank() -> "El teléfono es requerido"
            telefono.length != 10 -> "El teléfono debe tener 10 dígitos"
            !telefono.all { it.isDigit() } -> "El teléfono solo debe contener números"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "La contraseña es requerida"
            password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
            else -> null
        }
    }

    private fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        return when {
            confirmPassword.isBlank() -> "Confirma tu contraseña"
            password != confirmPassword -> "Las contraseñas no coinciden"
            else -> null
        }
    }

    private fun validateAllFields(): Boolean {
        val data = _uiState.value.registerData

        val displayNameError = validateDisplayName(data.displayName)
        val cedulaError = validateCedula(data.dni)
        val emailError = validateEmail(data.email)
        val telefonoError = validateTelefono(data.phone)
        val passwordError = validatePassword(data.password)
        val confirmPasswordError = validateConfirmPassword(data.password, data.confirmPassword)

        _uiState.update {
            it.copy(
                displayNameError = displayNameError,
                cedulaError = cedulaError,
                emailError = emailError,
                telefonoError = telefonoError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError,
                displayNameTouched = true,
                cedulaTouched = true,
                emailTouched = true,
                telefonoTouched = true,
                passwordTouched = true,
                confirmPasswordTouched = true
            )
        }

        return displayNameError == null &&
                cedulaError == null &&
                emailError == null &&
                telefonoError == null &&
                passwordError == null &&
                confirmPasswordError == null &&
                data.acceptTerms &&
                data.fcmToken.length > 100
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

                if (!isValidFcmToken(token)) {
                    Log.e("RegisterViewModel", "Token FCM inválido: $token")
                    return@launch
                }

                _uiState.update {
                    it.copy(
                        registerData = it.registerData.copy(
                            fcmToken = token!!,
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

    private fun isValidFcmToken(token: String?): Boolean {
        return token != null && token.contains(":") && token.length > 100
    }

    private fun registerUser() {
        if (!validateAllFields()) {
            return
        }

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

    private fun dismissDialog() {
        _uiState.update { it.copy(dialogState = DialogState.None) }
    }
}