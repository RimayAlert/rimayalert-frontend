package com.fazq.rimayalert.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fazq.rimayalert.features.alerts.ui.screen.AlertsScreen
import com.fazq.rimayalert.features.auth.ui.screens.LoginScreen
import com.fazq.rimayalert.features.auth.ui.screens.RegisterScreen
import com.fazq.rimayalert.features.home.ui.screen.HomeScreen
import com.fazq.rimayalert.features.splash.ui.screen.SplashScreen

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onForgotPasswordClick = {
                    // TODO: navController.navigate(Screen.ForgotPassword.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onTermsClick = {
                    // TODO: Implementar
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onCreateAlertClick = {
                    // TODO: navController.navigate(Screen.CreateAlert.route)
                },
                onAlertClick = {
                    // TODO: navController.navigate("${Screen.AlertDetail.route}/$alertId")
                },
                onNavigateToAlerts = {
                    navController.navigate(Screen.Alerts.route) {
                        popUpTo(Screen.Alerts.route) { inclusive = true }
                    }
                },
                onNavigateToMap = {
                    // TODO: navController.navigate(Screen.Map.route)
                },
                onNavigateToProfile = {
                    // TODO: navController.navigate(Screen.Profile.route)
                }
            )
        }
        composable(Screen.Alerts.route) {
            AlertsScreen(
                onNavigateToAlerts = {},
                onNavigateToMap = {},
                onNavigateToProfile = {},
                onNotificationClick = {}
            )
        }

    }
}