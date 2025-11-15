package com.fazq.rimayalert.features.maps.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fazq.rimayalert.core.ui.components.scaffold.AppBottomNavigationComponent
import com.fazq.rimayalert.core.ui.components.scaffold.AppScaffoldComponent
import com.fazq.rimayalert.core.ui.components.topBar.HomeTopBarComponent
import com.fazq.rimayalert.features.home.ui.viewmodel.HomeViewModel
import com.fazq.rimayalert.features.maps.ui.component.MapScreenContent
import com.fazq.rimayalert.features.maps.viewmodel.MapsViewModel

@Composable
fun MapScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToAlerts: () -> Unit = {},
    onNavigateToMap: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    homeViewModel: HomeViewModel = hiltViewModel(),
    mapsViewModel: MapsViewModel = hiltViewModel()
) {
    val mapsUiState by mapsViewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }


    AppScaffoldComponent(
        topBar = {
            HomeTopBarComponent("user name", onNotificationClick)
        },
        bottomBar = {
            AppBottomNavigationComponent(
                currentRoute = 2,
                onHomeClick = onNavigateToHome,
                onAlertsClick = onNavigateToAlerts,
                onMapClick = onNavigateToMap,
                onProfileClick = onNavigateToProfile
            )
        },
        snackbarHostState = snackbarHostState
    ) { paddingValues ->
        MapScreenContent(
            modifier = Modifier.padding(paddingValues),
            mapsUiState = mapsUiState,
            onEvent = mapsViewModel::onEvent
        )
    }
}