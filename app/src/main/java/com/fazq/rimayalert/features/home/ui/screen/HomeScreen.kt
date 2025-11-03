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
import com.fazq.rimayalert.core.ui.components.scaffold.AppBottomNavigation
import com.fazq.rimayalert.core.ui.components.scaffold.AppScaffold
import com.fazq.rimayalert.core.ui.components.topBar.HomeTopBar
import com.fazq.rimayalert.core.ui.extensions.getDisplayName
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
    onNotificationClick: () -> Unit = {},
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeState by homeViewModel.homeUiState.collectAsState()
    val user by homeViewModel.user.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var localUiState by remember { mutableStateOf(HomeUiState()) }
    val isLoading = homeState is BaseUiState.LoadingState

    LaunchedEffect(user) {
        user?.let { userData ->
            localUiState = localUiState.copy(userName = userData.getDisplayName())
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

    AppScaffold(
        topBar = {
            HomeTopBar(localUiState.userName, onNotificationClick)
        },
        bottomBar = {
            AppBottomNavigation(
                currentRoute = 0,
                onHomeClick = {},
                onAlertsClick = onNavigateToAlerts,
                onMapClick = onNavigateToMap,
                onProfileClick = onNavigateToProfile
            )
        },
        snackbarHostState = snackbarHostState
    ) { paddingValues ->
        HomeContent(
            paddingValues = paddingValues,
            uiState = localUiState,
            isLoading = isLoading,
            onCreateAlertClick = onCreateAlertClick,
            onAlertClick = onAlertClick,
            onRefresh = { homeViewModel.loadHomeData() }
        )
    }
}

