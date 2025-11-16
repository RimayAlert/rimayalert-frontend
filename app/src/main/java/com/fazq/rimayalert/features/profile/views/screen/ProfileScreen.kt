package com.fazq.rimayalert.features.profile.views.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fazq.rimayalert.core.ui.components.scaffold.AppBottomNavigationComponent
import com.fazq.rimayalert.core.ui.components.scaffold.AppScaffoldComponent
import com.fazq.rimayalert.core.ui.components.topBar.HomeTopBarComponent
import com.fazq.rimayalert.features.profile.views.component.ProfileContentComponent
import com.fazq.rimayalert.features.profile.views.viewModel.ProfileViewModel

@Composable
fun ProfileScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToAlerts: () -> Unit = {},
    onNavigateToMap: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    val profileUiState by profileViewModel.profileUiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }



    AppScaffoldComponent(
        topBar = {
            HomeTopBarComponent(profileUiState.userName, onNotificationClick)
        },
        bottomBar = {
            AppBottomNavigationComponent(
                currentRoute = 3,
                onHomeClick = onNavigateToHome,
                onAlertsClick = onNavigateToAlerts,
                onMapClick = onNavigateToMap,
                onProfileClick = onNavigateToProfile
            )
        },
        snackbarHostState = snackbarHostState
    ) { paddingValues ->
        ProfileContentComponent(
            profileUiState = profileUiState,
            paddingValues = paddingValues,
            onEvent = profileViewModel::onEvent
        )
    }
}