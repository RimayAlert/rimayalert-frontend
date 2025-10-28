package com.fazq.rimayalert.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fazq.rimayalert.features.auth.views.ui.main.screen.LoginScreen
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
                navController = navController,
                onRegisterClick = { navController.navigate("register") },
                onLoginClick = { email, password -> },
                onForgotPasswordClick = {}
            )
        }
        composable("register") {
            RegisterScreen(
                navController = navController,
                onRegisterSuccess = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onBackClick = { navController.popBackStack() },
                onTermsClick = {}
            )
        }
    }

}