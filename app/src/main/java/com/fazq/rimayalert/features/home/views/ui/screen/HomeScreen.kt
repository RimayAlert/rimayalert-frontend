package com.fazq.rimayalert.features.home.views.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.core.ui.theme.BackgroundLight
import com.fazq.rimayalert.features.home.views.ui.components.*
import com.fazq.rimayalert.features.home.views.ui.screen.states.HomeUiState
import com.fazq.rimayalert.features.home.views.viewmodel.HomeViewModel

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
    var localUiState by remember { mutableStateOf(HomeUiState()) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(homeState) {
        when (val state = homeState) {
            is BaseUiState.SuccessState<*> -> {
                // Actualizar UI con datos
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

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    homeState: BaseUiState,
    snackbarHostState: SnackbarHostState,
    onCreateAlertClick: () -> Unit,
    onAlertClick: (String) -> Unit,
    onNavigateToAlerts: () -> Unit,
    onNavigateToMap: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onRefresh: () -> Unit
) {
    val isLoading = homeState is BaseUiState.LoadingState

    Scaffold(
        topBar = {
            HomeTopBar(
                userName = uiState.userName,
                onNotificationClick = { /* TODO */ }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = 0,
                onHomeClick = { },
                onAlertsClick = onNavigateToAlerts,
                onMapClick = onNavigateToMap,
                onProfileClick = onNavigateToProfile
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundLight)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Resumen semanal
                WeeklySummaryCard(
                    alerts = uiState.weeklySummary.alerts,
                    resolved = uiState.weeklySummary.resolved,
                    pending = uiState.weeklySummary.pending,
                    averageTime = uiState.weeklySummary.averageTime,
                    lastDays = 7
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón crear alerta
                CreateAlertButton(
                    onClick = onCreateAlertClick,
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Actividad reciente
                RecentActivitySection(
                    activities = uiState.recentActivities,
                    onActivityClick = onAlertClick,
                    onRefresh = onRefresh,
                    isRefreshing = isLoading
                )

                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}