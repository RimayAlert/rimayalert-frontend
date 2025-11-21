package com.fazq.rimayalert.features.home.views.screen

import android.Manifest
import android.os.Build
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.fazq.rimayalert.core.states.DialogState
import com.fazq.rimayalert.core.ui.components.dialogs.ErrorDialogComponent
import com.fazq.rimayalert.core.ui.components.dialogs.SuccessDialogComponent
import com.fazq.rimayalert.core.ui.components.scaffold.AppBottomNavigationComponent
import com.fazq.rimayalert.core.ui.components.scaffold.AppScaffoldComponent
import com.fazq.rimayalert.core.ui.components.topBar.HomeTopBarComponent
import com.fazq.rimayalert.features.home.views.components.sections.HomeContent
import com.fazq.rimayalert.features.home.views.viewmodel.HomeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

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
    val snackbarHostState = remember { SnackbarHostState() }

    val warningMessage by homeViewModel.warningMessage.collectAsState()

    val notificationPermission = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS
    )

    val notificationGranted by homeViewModel.notificationGranted.collectAsState(initial = false)
    val notificationDeniedPermanent by homeViewModel.notificationDeniedPermanent.collectAsState(
        initial = false
    )

    /**
     * 1. PEDIR PERMISO AUTOMÁTICAMENTE SOLO 1 VEZ
     */
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= 33 &&
            !notificationGranted &&
            !notificationDeniedPermanent
        ) {
            notificationPermission.launchPermissionRequest()
        }
    }

    /**
     * 2. ESCUCHAR CAMBIO REAL DEL PERMISO DEL SISTEMA
     */
    LaunchedEffect(notificationPermission.status) {
        when {
            // PERMITIDO
            notificationPermission.status.isGranted -> {
                homeViewModel.setNotificationGranted()
            }

            notificationPermission.status.shouldShowRationale -> {
                homeViewModel.showWarning("Para recibir alertas, permite las notificaciones.")
            }

            !notificationPermission.status.isGranted &&
                    !notificationPermission.status.shouldShowRationale -> {
                homeViewModel.showWarning("Puedes activar las notificaciones desde Ajustes.")
                homeViewModel.setNotificationDeniedPermanent()
            }
        }
    }

    LaunchedEffect(warningMessage) {
        warningMessage?.let { msg ->
            snackbarHostState.showSnackbar(
                message = msg,
                duration = SnackbarDuration.Short
            )
            homeViewModel.showWarning(null)
        }
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

        else -> Unit
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
