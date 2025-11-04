package com.fazq.rimayalert.core.ui.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object Profile : Screen("profile")
    data object Map : Screen("map")
    data object Alerts : Screen("alerts")
    data object ForgotPassword : Screen("forgot_password")
}