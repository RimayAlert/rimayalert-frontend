package com.fazq.rimayalert.features.home.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.fazq.rimayalert.core.ui.components.scaffold.AppBottomNavigationComponent
import com.fazq.rimayalert.core.ui.components.scaffold.AppScaffoldComponent
import com.fazq.rimayalert.core.ui.components.topBar.HomeTopBarComponent
import com.fazq.rimayalert.features.home.ui.components.sections.HomeContent
import com.fazq.rimayalert.features.home.ui.components.dialogs.LocationPermissionDialogComponent
import com.fazq.rimayalert.features.home.ui.states.rememberHomeScreenState
import com.fazq.rimayalert.features.home.ui.viewmodel.HomeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
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
    val screenState = rememberHomeScreenState(viewModel = homeViewModel)

    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(locationPermissionsState.allPermissionsGranted) {
        if (locationPermissionsState.allPermissionsGranted) {
            screenState.handleLocationPermissionsResult(
                permissions = locationPermissionsState.permissions.associate {
                    it.permission to it.status.isGranted
                },
                shouldShowRationale = locationPermissionsState.shouldShowRationale
            )
        }
    }

    if (screenState.showLocationDialog) {
        LocationPermissionDialogComponent(
            onAllowClick = {
                locationPermissionsState.launchMultiplePermissionRequest()
            },
            onDismiss = screenState::onDismissLocationDialog
        )
    }

    AppScaffoldComponent(
        topBar = {
            HomeTopBarComponent(
                userName = screenState.localUiState.userName,
                onNotificationClick = onNotificationClick
            )
        },
        bottomBar = {
            AppBottomNavigationComponent(
                currentRoute = 0,
                onHomeClick = {},
                onAlertsClick = onNavigateToAlerts,
                onMapClick = onNavigateToMap,
                onProfileClick = onNavigateToProfile
            )
        },
        snackbarHostState = screenState.snackbarHostState
    ) { paddingValues ->
        HomeContent(
            paddingValues = paddingValues,
            uiState = screenState.localUiState,
            isLoading = screenState.isLoading,
            onCreateAlertClick = onCreateAlertClick,
            onAlertClick = onAlertClick,
            onRefresh = screenState::onRefresh
        )
    }
}