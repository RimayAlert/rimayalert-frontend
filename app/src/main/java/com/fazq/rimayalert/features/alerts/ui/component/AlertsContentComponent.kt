package com.fazq.rimayalert.features.alerts.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fazq.rimayalert.core.ui.components.topBar.HomeTopBar
import com.fazq.rimayalert.features.home.ui.states.HomeUiState

@Composable
fun AlertsContentComponent(
    uiState: HomeUiState,

) {
    Scaffold(
        topBar = {
            HomeTopBar(
                userName = "Alerts",
                onNotificationClick = { /* TODO */ }

            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Aquí puedes agregar el contenido específico de la pantalla de Alerts
        }
    }

}