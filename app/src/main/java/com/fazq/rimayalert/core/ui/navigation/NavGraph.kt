package com.fazq.rimayalert.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fazq.rimayalert.features.alerts.views.screen.AlertsScreen
import com.fazq.rimayalert.features.auth.views.screens.LoginScreen
import com.fazq.rimayalert.features.auth.views.screens.RegisterScreen
import com.fazq.rimayalert.features.home.views.screen.HomeScreen
import com.fazq.rimayalert.features.maps.views.screen.MapScreen
import com.fazq.rimayalert.features.profile.views.screen.ProfileScreen
import com.fazq.rimayalert.features.splash.ui.screen.SplashScreen

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onRegisterClick = { navController.navigateSingleTop(Screen.Register) },
                onLoginSuccess = {
                    navController.navigateAndClearBackStackTo(
                        screen = Screen.Home,
                        popUpToRoute = Screen.Login.route
                    )
                },
                onForgotPasswordClick = { navController.navigateSingleTop(Screen.ForgotPassword) }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigateAndClearBackStackTo(
                        screen = Screen.Login,
                        popUpToRoute = Screen.Register.route
                    )
                },
                onBackClick = { navController.popBackStack() },
                onLoginClick = {
                    navController.navigateAndClearBackStackTo(
                        screen = Screen.Login,
                        popUpToRoute = Screen.Register.route
                    )
                },
                onTermsClick = {
                    // TODO: navController.navigateSingleTop(Screen.Terms)
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onCreateAlertClick = { navController.navigateToScreen(Screen.Alerts) },
                onAlertClick = { alertId ->
                    // TODO: navController.navigate("alert_detail/$alertId")
                },
                onNavigateToAlerts = { navController.navigateToScreen(Screen.Alerts) },
                onNavigateToMap = { navController.navigateToScreen(Screen.Map) },
                onNavigateToProfile = { navController.navigateToScreen(Screen.Profile) }
            )
        }

        composable(Screen.Alerts.route) {
            AlertsScreen(
                onNavigateToHome = { navController.navigateToScreen(Screen.Home) },
                onNavigateToAlerts = {},
                onNavigateToMap = { navController.navigateToScreen(Screen.Map) },
                onNavigateToProfile = { navController.navigateToScreen(Screen.Profile) },
                onNotificationClick = {}
            )
        }

        composable(Screen.Map.route) {
            MapScreen(
                onNavigateToHome = { navController.navigateToScreen(Screen.Home) },
                onNavigateToAlerts = { navController.navigateToScreen(Screen.Alerts) },
                onNavigateToMap = {},
                onNavigateToProfile = { navController.navigateToScreen(Screen.Profile) },
                onNotificationClick = {}
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateToHome = { navController.navigateToScreen(Screen.Home) },
                onNavigateToAlerts = { navController.navigateToScreen(Screen.Alerts) },
                onNavigateToMap = { navController.navigateToScreen(Screen.Map) },
                onNavigateToProfile = {},
                onNavigateToLogin = {
                    navController.navigateAndClearBackStackTo(
                        screen = Screen.Login,
                        popUpToRoute = Screen.Profile.route
                    )
                },
                onNotificationClick = {}
            )
        }
    }
}