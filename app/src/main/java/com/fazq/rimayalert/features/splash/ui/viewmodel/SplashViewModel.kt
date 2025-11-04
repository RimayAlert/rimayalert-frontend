package com.fazq.rimayalert.features.splash.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.utils.TokenManager
import com.fazq.rimayalert.features.splash.ui.states.SplashNavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenManager: TokenManager,
) : ViewModel() {

    private val _navigationState =
        MutableStateFlow<SplashNavigationState>(SplashNavigationState.Initial)
    val navigationState: StateFlow<SplashNavigationState> = _navigationState.asStateFlow()

    init {
        checkAuthenticationStatus()
    }

    private fun checkAuthenticationStatus() {
        viewModelScope.launch {
            val token = tokenManager.getToken()
            _navigationState.value = if (token.isNotEmpty()) {
                SplashNavigationState.NavigateToHome
            } else {
                SplashNavigationState.NavigateToLogin
            }
        }
    }

    fun resetNavigationState() {
        _navigationState.value = SplashNavigationState.Initial
    }

}
