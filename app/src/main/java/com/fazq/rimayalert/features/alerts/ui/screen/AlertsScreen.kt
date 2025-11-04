package com.fazq.rimayalert.features.alerts.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.core.ui.components.scaffold.AppBottomNavigation
import com.fazq.rimayalert.core.ui.components.scaffold.AppScaffold
import com.fazq.rimayalert.core.ui.components.topBar.HomeTopBar
import com.fazq.rimayalert.core.ui.extensions.getDisplayName
import com.fazq.rimayalert.features.alerts.ui.component.AlertsContentComponent
import com.fazq.rimayalert.features.alerts.ui.viewmodel.AlertViewModel
import com.fazq.rimayalert.features.home.ui.states.HomeUiState
import com.fazq.rimayalert.features.home.ui.viewmodel.HomeViewModel

@Composable
fun AlertsScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToAlerts: () -> Unit = {},
    onNavigateToMap: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    homeViewModel: HomeViewModel = hiltViewModel(),
    alertViewModel: AlertViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val user by homeViewModel.user.collectAsStateWithLifecycle()
    val alertUiState by alertViewModel.alertUiState.collectAsStateWithLifecycle()
    val sendAlertState by alertViewModel.sendAlertState.collectAsStateWithLifecycle()
    var localUiState by remember { mutableStateOf(HomeUiState()) }



    LaunchedEffect(user) {
        user?.let { userData ->
            localUiState = localUiState.copy(userName = userData.getDisplayName())
        }
    }

    LaunchedEffect(sendAlertState) {
        when (sendAlertState) {
            is BaseUiState.SuccessState<*> -> {
                val message = (sendAlertState as BaseUiState.SuccessState<*>).data as? String
                    ?: "Alerta enviada exitosamente"
                snackbarHostState.showSnackbar(
                    message = message,
                    withDismissAction = true
                )
                alertViewModel.resetState()
                onNavigateToHome()
            }

            is BaseUiState.ErrorState -> {
                snackbarHostState.showSnackbar(
                    message = (sendAlertState as BaseUiState.ErrorState).message,
                    duration = SnackbarDuration.Long
                )
                alertViewModel.resetState()
            }

            is BaseUiState.LoadingState -> {}
            is BaseUiState.EmptyState -> {}
        }
    }


    AppScaffold(
        topBar = {
            HomeTopBar(localUiState.userName, onNotificationClick)
        },
        bottomBar = {
            AppBottomNavigation(
                currentRoute = 1,
                onHomeClick = onNavigateToHome,
                onAlertsClick = onNavigateToAlerts,
                onMapClick = onNavigateToMap,
                onProfileClick = onNavigateToProfile
            )
        },
        snackbarHostState = snackbarHostState
    ) { paddingValues ->
        AlertsContentComponent(
            modifier = Modifier.padding(paddingValues),
            uiState = alertUiState,
            onTypeSelected = alertViewModel::onTypeSelected,
            onDescriptionChanged = alertViewModel::onDescriptionChanged,
            onUploadImage = alertViewModel::onUploadImage,
            onSendAlert = alertViewModel::sendAlert,
            onOpenCamera = alertViewModel::onOpenCamera,
            onLocationEdit = alertViewModel::onLocationEdit,
            onUseMap = alertViewModel::onUseMap,
        )
    }
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun AlertsScreenPreview() {
//    RimayAlertTheme {
//        val fakeUiState = HomeUiState(userName = "Dev")
//        AppScaffold(
//            topBar = {
//                HomeTopBar(fakeUiState.userName, onNotificationClick = {})
//            },
//            bottomBar = {
//                AppBottomNavigation(
//                    currentRoute = 1,
//                    onHomeClick = {},
//                    onAlertsClick = {},
//                    onMapClick = {},
//                    onProfileClick = {}
//                )
//            },
//            snackbarHostState = remember { SnackbarHostState() }
//        ) { paddingValues ->
//            AlertsContentComponent(uiState = fakeUiState)
//        }
//    }
//}
