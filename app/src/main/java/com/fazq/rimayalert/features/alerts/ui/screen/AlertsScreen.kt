package com.fazq.rimayalert.features.alerts.ui.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fazq.rimayalert.core.ui.components.scaffold.AppBottomNavigation
import com.fazq.rimayalert.core.ui.components.scaffold.AppScaffold
import com.fazq.rimayalert.core.ui.components.topBar.HomeTopBar
import com.fazq.rimayalert.core.ui.extensions.getDisplayName
import com.fazq.rimayalert.core.ui.theme.RimayAlertTheme
import com.fazq.rimayalert.features.alerts.ui.component.AlertsContentComponent
import com.fazq.rimayalert.features.home.ui.states.HomeUiState
import com.fazq.rimayalert.features.home.ui.viewmodel.HomeViewModel


@Composable
fun AlertsScreen(
    onNavigateToAlerts: () -> Unit = {},
    onNavigateToMap: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val user by homeViewModel.user.collectAsStateWithLifecycle()
    var localUiState by remember { mutableStateOf(HomeUiState()) }

    LaunchedEffect(user) {
        user?.let { userData ->
            localUiState = localUiState.copy(userName = userData.getDisplayName())
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
        AlertsContentComponent(
            uiState = localUiState,
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AlertsScreenPreview() {
    RimayAlertTheme {
        val fakeUiState = HomeUiState(userName = "Dev")
        AppScaffold(
            topBar = {
                HomeTopBar(fakeUiState.userName, onNotificationClick = {})
            },
            bottomBar = {
                AppBottomNavigation(
                    currentRoute = 1,
                    onHomeClick = {},
                    onAlertsClick = {},
                    onMapClick = {},
                    onProfileClick = {}
                )
            },
            snackbarHostState = remember { SnackbarHostState() }
        ) { paddingValues ->
            AlertsContentComponent(uiState = fakeUiState)
        }
    }
}
