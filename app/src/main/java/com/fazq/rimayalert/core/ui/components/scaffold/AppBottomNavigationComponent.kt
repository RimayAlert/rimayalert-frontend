package com.fazq.rimayalert.core.ui.components.scaffold

import androidx.compose.runtime.Composable
import com.fazq.rimayalert.core.ui.components.navigation.BottomNavigationBarComponent

@Composable
fun AppBottomNavigationComponent(
    currentRoute: Int,
    onHomeClick: () -> Unit,
    onAlertsClick: () -> Unit,
    onMapClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    BottomNavigationBarComponent(
        selectedTab = currentRoute,
        onHomeClick = onHomeClick,
        onAlertsClick = onAlertsClick,
        onMapClick = onMapClick,
        onProfileClick = onProfileClick
    )
}