package com.fazq.rimayalert.core.ui.components.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.Dimensions

@Composable
fun BottomNavigationBarComponent(
    selectedTab: Int,
    onHomeClick: () -> Unit,
    onAlertsClick: () -> Unit,
    onMapClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.paddingDefault, vertical = Dimensions.paddingCompact),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Dimensions.cornerRadiusPill),
            color = AppColors.surfaceLight,
            shadowElevation = Dimensions.elevationHigh
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimensions.paddingCompact,
                        vertical = Dimensions.paddingDefault
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationItemComponent(
                    icon = if (selectedTab == 0) Icons.Filled.Home else Icons.Outlined.Home,
                    label = "Inicio",
                    isSelected = selectedTab == 0,
                    onClick = onHomeClick
                )

                NavigationItemComponent(
                    icon = if (selectedTab == 1) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                    label = "Alertas",
                    isSelected = selectedTab == 1,
                    onClick = onAlertsClick
                )

                NavigationItemComponent(
                    icon = if (selectedTab == 2) Icons.Filled.Map else Icons.Outlined.Map,
                    label = "Mapa",
                    isSelected = selectedTab == 2,
                    onClick = onMapClick
                )

                NavigationItemComponent(
                    icon = if (selectedTab == 3) Icons.Filled.Person else Icons.Outlined.Person,
                    label = "Perfil",
                    isSelected = selectedTab == 3,
                    onClick = onProfileClick
                )
            }
        }
    }
}