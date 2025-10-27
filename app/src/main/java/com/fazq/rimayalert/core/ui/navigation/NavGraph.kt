package com.fazq.rimayalert.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fazq.rimayalert.features.auth.ui.LoginScreen
import com.fazq.rimayalert.features.auth.views.ui.main.screen.RegisterScreen
import com.fazq.rimayalert.features.splash.ui.SplashScreen

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") { SplashScreen(navController) }

        composable("login") {
            LoginScreen(
                onRegisterClick = { navController.navigate("register") },
                onLoginClick = { email, password -> },
                onForgotPasswordClick = {}
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("login?username=$it") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onLoginClick = { navController.navigate("login") },
                onBackClick = { navController.popBackStack() },
                onTermsClick = {}
            )
        }
    }

}