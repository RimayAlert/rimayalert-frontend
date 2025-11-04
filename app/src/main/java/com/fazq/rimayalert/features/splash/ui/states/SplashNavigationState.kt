package com.fazq.rimayalert.features.splash.ui.states

sealed class SplashNavigationState {
    data object Initial : SplashNavigationState()
    data object NavigateToHome : SplashNavigationState()
    data object NavigateToLogin : SplashNavigationState()
}