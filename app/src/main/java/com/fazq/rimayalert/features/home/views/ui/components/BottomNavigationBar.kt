package com.fazq.rimayalert.features.home.views.ui.components

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onHomeClick: () -> Unit,
    onAlertsClick: () -> Unit,
    onMapClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = onHomeClick,
            icon = { Text("🏠", fontSize = 24.sp) },
            label = { Text("Inicio", fontSize = 12.sp) }
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = onAlertsClick,
            icon = { Text("🔔", fontSize = 24.sp) },
            label = { Text("Alertas", fontSize = 12.sp) }
        )
        NavigationBarItem(
            selected = selectedTab == 2,
            onClick = onMapClick,
            icon = { Text("🗺️", fontSize = 24.sp) },
            label = { Text("Mapa", fontSize = 12.sp) }
        )
        NavigationBarItem(
            selected = selectedTab == 3,
            onClick = onProfileClick,
            icon = { Text("👤", fontSize = 24.sp) },
            label = { Text("Perfil", fontSize = 12.sp) }
        )
    }
}