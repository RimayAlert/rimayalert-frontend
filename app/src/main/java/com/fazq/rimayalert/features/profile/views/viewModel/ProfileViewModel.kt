package com.fazq.rimayalert.features.profile.views.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.preferences.UserPreferencesManager
import com.fazq.rimayalert.core.ui.extensions.getDisplayName
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
            is ProfileEvent.OnLogoutClick -> handleLogout()
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

    private fun handleLogout() {
        viewModelScope.launch {
            // TODO: Implementar logout
            userPreferencesManager.clearUser()
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