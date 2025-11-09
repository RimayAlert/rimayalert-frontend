package com.fazq.rimayalert.features.maps.ui.states

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fazq.rimayalert.core.ui.components.scaffold.AppBottomNavigationComponent
import com.fazq.rimayalert.core.ui.components.scaffold.AppScaffoldComponent
import com.fazq.rimayalert.core.ui.components.topBar.HomeTopBarComponent
import com.fazq.rimayalert.core.ui.extensions.getDisplayName
import com.fazq.rimayalert.features.home.ui.states.HomeUiState
import com.fazq.rimayalert.features.home.ui.viewmodel.HomeViewModel

@Composable
fun MapScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToAlerts: () -> Unit = {},
    onNavigateToMap: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},

    onNotificationClick: () -> Unit = {},
    homeViewModel: HomeViewModel = hiltViewModel(),

    ) {

    val user by homeViewModel.user.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var localUiState by remember { mutableStateOf(HomeUiState()) }



    LaunchedEffect(user) {
        user?.let { userData ->
            localUiState = localUiState.copy(userName = userData.getDisplayName())
        }
    }


    AppScaffoldComponent(
        topBar = {
            HomeTopBarComponent(localUiState.userName, onNotificationClick)
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
    ) {

    }
}