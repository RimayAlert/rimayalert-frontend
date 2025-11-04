package com.fazq.rimayalert.features.auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.preferences.UserPreferencesManager
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.utils.TokenManager
import com.fazq.rimayalert.features.auth.domain.model.AuthModel
import com.fazq.rimayalert.features.auth.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val tokenManager: TokenManager,
    private val userPreferencesManager: UserPreferencesManager

) : ViewModel() {

    private val _authUiState = MutableStateFlow<BaseUiState>(BaseUiState.EmptyState)
    val authUiState: StateFlow<BaseUiState> = _authUiState.asStateFlow()


    fun auth(authParam: AuthModel) {
        viewModelScope.launch {
            _authUiState.value = BaseUiState.LoadingState
            when (val responseState = authUseCase.auth(authParam)) {
                is DataState.Success -> _authUiState.value =
                    BaseUiState.SuccessState(responseState.data)

                is DataState.Error -> _authUiState.value =
                    BaseUiState.ErrorState(responseState.message)
            }

        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenManager.clearToken()
            userPreferencesManager.clearUser()
            _authUiState.value = BaseUiState.EmptyState
        }
    }

    fun resetState() {
        _authUiState.value = BaseUiState.EmptyState
    }

}