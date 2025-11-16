package com.fazq.rimayalert.features.profile.views.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.preferences.UserPreferencesManager
import com.fazq.rimayalert.core.states.DialogState
import com.fazq.rimayalert.core.ui.extensions.getDisplayName
import com.fazq.rimayalert.core.utils.TokenManager
import com.fazq.rimayalert.features.profile.views.event.ProfileEvent
import com.fazq.rimayalert.features.profile.views.state.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState

    init {
        observeUser()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnChangePasswordClick -> handleChangePassword()
            is ProfileEvent.OnAboutAppClick -> handleAboutApp()
            is ProfileEvent.OnLogoutClick -> handleLogoutClick()
            is ProfileEvent.OnDismissDialog -> handleDismissDialog()
            is ProfileEvent.OnNotificationToggle -> handleNotificationToggle()
            is ProfileEvent.OnCommunityClick -> handleCommunityClick()
            is ProfileEvent.OnOpenIncidentsMapClick -> handleOpenIncidentsMap()
        }
    }

    private fun observeUser() {
        viewModelScope.launch {
            userPreferencesManager.user.collect { user ->
                _profileUiState.value = _profileUiState.value.copy(userName = user.getDisplayName())
            }
        }
    }

    private fun handleChangePassword() {
        // TODO: Implementar cambio de contraseña
    }

    private fun handleAboutApp() {
        // TODO: Implementar acerca de la app
    }

    private fun handleLogoutClick() {
        _profileUiState.value = _profileUiState.value.copy(
            dialogState = DialogState.Confirmation(
                title = "Cerrar Sesión",
                message = "¿Estás seguro de que deseas cerrar sesión?",
                onConfirm = { confirmLogout() }
            )
        )
    }

    private fun handleDismissDialog() {
        _profileUiState.value = _profileUiState.value.copy(dialogState = DialogState.None)
    }

    private fun confirmLogout() {
        viewModelScope.launch {
            _profileUiState.value = _profileUiState.value.copy(
                dialogState = DialogState.None,
                isLoading = true
            )

            try {
                tokenManager.clearToken()
                _profileUiState.value = _profileUiState.value.copy(
                    shouldNavigateToLogin = true,
                    isLoading = false
                )
            } catch (e: Exception) {
                _profileUiState.value = _profileUiState.value.copy(
                    dialogState = DialogState.Error(
                        title = "Error",
                        message = "No se pudo cerrar sesión: ${e.message}"
                    ),
                    isLoading = false
                )
            }
        }
    }

    private fun handleNotificationToggle() {
        _profileUiState.value = _profileUiState.value.copy(
            notificationsEnabled = !_profileUiState.value.notificationsEnabled
        )
    }

    private fun handleCommunityClick() {
        // TODO: Implementar navegación a comunidad
    }

    private fun handleOpenIncidentsMap() {
        // TODO: Implementar apertura del mapa
    }
}