package com.fazq.rimayalert.features.splash.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fazq.rimayalert.R
import com.fazq.rimayalert.core.ui.navigation.Screen
import com.fazq.rimayalert.features.splash.ui.states.SplashNavigationState
import com.fazq.rimayalert.features.splash.ui.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val navigationState by viewModel.navigationState.collectAsStateWithLifecycle()

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.alert_splash)
    )

    val animState = animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1800)

        when (navigationState) {
            is SplashNavigationState.NavigateToHome -> {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }

            is SplashNavigationState.NavigateToLogin,
            SplashNavigationState.Initial -> {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        }

        viewModel.resetNavigationState()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { animState.progress },
            modifier = Modifier
                .fillMaxWidth(0.7f)
        )
    }
}

