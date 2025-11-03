package com.fazq.rimayalert.features.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.preferences.UserPreferencesManager
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.features.auth.domain.model.User
import com.fazq.rimayalert.features.auth.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<BaseUiState>(BaseUiState.EmptyState)
    val homeUiState: StateFlow<BaseUiState> = _homeUiState.asStateFlow()

    val user: StateFlow<User?> = userPreferencesManager.user
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )


    fun loadHomeData() {
        viewModelScope.launch {
            _homeUiState.value = BaseUiState.LoadingState
            _homeUiState.value = BaseUiState.SuccessState(data = "Home Data Loaded")

        }
    }

    fun resetState() {
        _homeUiState.value = BaseUiState.EmptyState
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
//            authUseCase.logout()
            onLogoutSuccess()
        }
    }
}