package com.fazq.rimayalert.features.auth.views.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.preferences.UserPreferencesManager
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.auth.domain.model.AuthModel
import com.fazq.rimayalert.features.auth.domain.usecase.AuthUseCase
import com.fazq.rimayalert.features.auth.views.event.LoginEvent
import com.fazq.rimayalert.features.auth.views.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val authUseCase: AuthUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.SaveLocation -> saveLocationToPreferences(event.latitude, event.longitude)


            is LoginEvent.UsernameChanged -> {
                _uiState.update { it.copy(userName = event.username) }
            }

            is LoginEvent.PasswordChanged -> {
                _uiState.update { it.copy(password = event.password) }
            }

            LoginEvent.LoginButtonClicked -> login()

            LoginEvent.ClearErrorMessage -> {
                _uiState.update { it.copy(errorMessage = null) }
            }

            LoginEvent.ClearSuccessMessage -> {
                _uiState.update { it.copy(successMessage = null) }
            }

            LoginEvent.PermissionGranted -> {
                _uiState.update { it.copy(hasLocationPermission = true) }
            }

            LoginEvent.PermissionDenied -> {
                _uiState.update { it.copy(hasLocationPermission = false) }
            }

            LoginEvent.PermissionRequestAttempt -> {
                _uiState.update { it.copy(hasAskedPermissionBefore = true) }
            }

            else -> {}
        }
    }

    private fun saveLocationToPreferences(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            userPreferencesManager.saveLocation(latitude, longitude)
        }
    }

    fun setLocationPermission(granted: Boolean) {
        _uiState.update {
            it.copy(hasLocationPermission = granted)
        }
    }

    private fun login() {
        val username = uiState.value.userName.trim()
        val password = uiState.value.password.trim()
        when {
            username.isEmpty() -> {
                _uiState.update {
                    it.copy(errorMessage = "El nombre de usuario no puede estar vacío.")
                }
                return
            }

            password.isEmpty() -> {
                _uiState.update {
                    it.copy(errorMessage = "La contraseña no puede estar vacía.")
                }
                return
            }
        }
        _uiState.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
                successMessage = null,
                loginSuccess = false
            )
        }

        viewModelScope.launch {
            val authParam = AuthModel(uiState.value.userName, password = uiState.value.password)
            when (val result = authUseCase.auth(authParam)) {
                is DataState.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true,
                            successMessage = "Inicio de sesión exitoso"
                        )
                    }
                }

                is DataState.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginSuccess = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }
}