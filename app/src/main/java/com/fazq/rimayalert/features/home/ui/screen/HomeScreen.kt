package com.fazq.rimayalert.features.home.ui.screen

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.features.home.ui.components.HomeContent
import com.fazq.rimayalert.features.home.ui.states.HomeUiState
import com.fazq.rimayalert.features.home.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onCreateAlertClick: () -> Unit = {},
    onAlertClick: (String) -> Unit = {},
    onNavigateToAlerts: () -> Unit = {},
    onNavigateToMap: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeState by homeViewModel.homeUiState.collectAsState()
    val user by homeViewModel.user.collectAsStateWithLifecycle()
    var localUiState by remember { mutableStateOf(HomeUiState()) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(user) {
        user?.let { userData ->
            val displayName = userData.aliasName?.takeIf { it.isNotEmpty() }
                ?: userData.fullName?.takeIf { it.isNotEmpty() }
                ?: userData.username
                ?: "Usuario"

            localUiState = localUiState.copy(userName = displayName)
        }
    }

    LaunchedEffect(homeState) {
        when (val state = homeState) {
            is BaseUiState.SuccessState<*> -> {
                homeViewModel.resetState()
            }

            is BaseUiState.ErrorState -> {
                snackbarHostState.showSnackbar(
                    message = state.message,
                    duration = SnackbarDuration.Long
                )
                homeViewModel.resetState()
            }

            else -> {}
        }
    }

    HomeContent(
        uiState = localUiState,
        homeState = homeState,
        snackbarHostState = snackbarHostState,
        onCreateAlertClick = onCreateAlertClick,
        onAlertClick = onAlertClick,
        onNavigateToAlerts = onNavigateToAlerts,
        onNavigateToMap = onNavigateToMap,
        onNavigateToProfile = onNavigateToProfile,
        onRefresh = { homeViewModel.loadHomeData() }
    )
}

