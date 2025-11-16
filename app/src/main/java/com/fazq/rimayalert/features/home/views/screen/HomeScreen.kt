package com.fazq.rimayalert.features.home.views.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.fazq.rimayalert.core.states.DialogState
import com.fazq.rimayalert.core.ui.components.dialogs.ErrorDialogComponent
import com.fazq.rimayalert.core.ui.components.dialogs.SuccessDialogComponent
import com.fazq.rimayalert.core.ui.components.scaffold.AppBottomNavigationComponent
import com.fazq.rimayalert.core.ui.components.scaffold.AppScaffoldComponent
import com.fazq.rimayalert.core.ui.components.topBar.HomeTopBarComponent
import com.fazq.rimayalert.features.home.views.components.dialogs.LocationPermissionDialogComponent
import com.fazq.rimayalert.features.home.views.components.sections.HomeContent
import com.fazq.rimayalert.features.home.views.event.HomeEvent
import com.fazq.rimayalert.features.home.views.viewmodel.HomeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
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
    val homeUiState by homeViewModel.homeState.collectAsState()
    var showLocationDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }


    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(homeUiState.hasCommunity, homeUiState.communityCheckCompleted) {
        showLocationDialog = !homeUiState.hasCommunity &&
                !homeUiState.communityCheckCompleted &&
                !locationPermissionsState.allPermissionsGranted
    }

    LaunchedEffect(locationPermissionsState.allPermissionsGranted) {
        if (locationPermissionsState.allPermissionsGranted &&
            !homeUiState.hasCommunity &&
            !homeUiState.communityCheckCompleted) {
            homeViewModel.onEvent(HomeEvent.ValidateOrAssignCommunity)
        }
    }

    if (showLocationDialog) {
        LocationPermissionDialogComponent(
            onAllowClick = {
                locationPermissionsState.launchMultiplePermissionRequest()
                showLocationDialog = false
            },
            onDismiss = {showLocationDialog = false}
        )
    }

    when (val dialog = homeUiState.dialogState) {
        is DialogState.Error -> {
            ErrorDialogComponent(
                openDialog = true,
                title = dialog.title,
                message = dialog.message,
                onDismiss = { homeViewModel.dismissDialog() }
            )
        }

        is DialogState.Success -> {
            SuccessDialogComponent(
                openDialog = true,
                title = dialog.title,
                message = dialog.message,
                onDismiss = { homeViewModel.dismissDialog() }
            )
        }

        else -> {}
    }


    AppScaffoldComponent(
        topBar = {
            HomeTopBarComponent(
                userName = homeUiState.userName,
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
        snackbarHostState = snackbarHostState
    ) { paddingValues ->
        HomeContent(
            paddingValues = paddingValues,
            uiState = homeUiState,
            isLoading = homeUiState.isLoadingHome,
            onCreateAlertClick = onCreateAlertClick,
            onAlertClick = onAlertClick,
            onRefresh = { homeViewModel.loadHomeData() }
        )
    }
}