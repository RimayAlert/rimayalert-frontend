package com.fazq.rimayalert.features.home.views.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        color = Color.White
    ) {
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp,
            modifier = Modifier.height(70.dp)
        ) {
            NavigationBarItem(
                selected = selectedTab == 0,
                onClick = onHomeClick,
                icon = {
                    Icon(
                        imageVector = if (selectedTab == 0) Icons.Filled.Home else Icons.Outlined.Home,
                        contentDescription = "Inicio",
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = {
                    Text(
                        "Inicio",
                        fontSize = 12.sp,
                        fontWeight = if (selectedTab == 0) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6366F1),
                    selectedTextColor = Color(0xFF6366F1),
                    unselectedIconColor = Color(0xFF94A3B8),
                    unselectedTextColor = Color(0xFF94A3B8),
                    indicatorColor = Color(0xFFF1F5F9)
                )
            )

            NavigationBarItem(
                selected = selectedTab == 1,
                onClick = onAlertsClick,
                icon = {
                    Icon(
                        imageVector = if (selectedTab == 1) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                        contentDescription = "Alertas",
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = {
                    Text(
                        "Alertas",
                        fontSize = 12.sp,
                        fontWeight = if (selectedTab == 1) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6366F1),
                    selectedTextColor = Color(0xFF6366F1),
                    unselectedIconColor = Color(0xFF94A3B8),
                    unselectedTextColor = Color(0xFF94A3B8),
                    indicatorColor = Color(0xFFF1F5F9)
                )
            )

            NavigationBarItem(
                selected = selectedTab == 2,
                onClick = onMapClick,
                icon = {
                    Icon(
                        imageVector = if (selectedTab == 2) Icons.Filled.Map else Icons.Outlined.Map,
                        contentDescription = "Mapa",
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = {
                    Text(
                        "Mapa",
                        fontSize = 12.sp,
                        fontWeight = if (selectedTab == 2) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6366F1),
                    selectedTextColor = Color(0xFF6366F1),
                    unselectedIconColor = Color(0xFF94A3B8),
                    unselectedTextColor = Color(0xFF94A3B8),
                    indicatorColor = Color(0xFFF1F5F9)
                )
            )

            NavigationBarItem(
                selected = selectedTab == 3,
                onClick = onProfileClick,
                icon = {
                    Icon(
                        imageVector = if (selectedTab == 3) Icons.Filled.Person else Icons.Outlined.Person,
                        contentDescription = "Perfil",
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = {
                    Text(
                        "Perfil",
                        fontSize = 12.sp,
                        fontWeight = if (selectedTab == 3) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6366F1),
                    selectedTextColor = Color(0xFF6366F1),
                    unselectedIconColor = Color(0xFF94A3B8),
                    unselectedTextColor = Color(0xFF94A3B8),
                    indicatorColor = Color(0xFFF1F5F9)
                )
            )
        }
    }
}
