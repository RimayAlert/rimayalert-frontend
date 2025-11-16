package com.fazq.rimayalert.features.profile.views.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.preferences.UserPreferencesManager
import com.fazq.rimayalert.core.ui.extensions.getDisplayName
import com.fazq.rimayalert.features.alerts.views.event.AlertEvent
import com.fazq.rimayalert.features.profile.views.state.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
) : ViewModel() {

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState

    init {
        observeUser()
    }

    fun onEvent(event: AlertEvent) {

    }

    private fun observeUser() {
        viewModelScope.launch {
            userPreferencesManager.user.collect { user ->
                _profileUiState.value = _profileUiState.value.copy(userName = user.getDisplayName())
            }
        }
    }
}