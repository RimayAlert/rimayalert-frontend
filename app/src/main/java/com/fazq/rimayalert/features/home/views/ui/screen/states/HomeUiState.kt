package com.fazq.rimayalert.features.home.views.ui.screen.states

import com.fazq.rimayalert.features.home.domain.model.ActivityItemModel
import com.fazq.rimayalert.features.home.domain.model.AlertStatus

data class HomeUiState(
    val userName: String = "Jayden",
    val weeklySummary: WeeklySummaryUiState = WeeklySummaryUiState(),
    val recentActivities: List<ActivityItemModel> = emptyList(),
    val isRefreshing: Boolean = false
)

data class WeeklySummaryUiState(
    val alerts: Int = 12,
    val resolved: Int = 9,
    val pending: Int = 3,
    val averageTime: String = "1h 22m"
)

// Datos de ejemplo para pruebas
fun getDefaultActivities() = listOf(
    ActivityItemModel(
        id = "1",
        title = "Emergencia médica",
        subtitle = "Edif. A - 2°B",
        time = "Ahora",
        status = AlertStatus.EMERGENCY
    ),
    ActivityItemModel(
        id = "2",
        title = "Ruido excesivo",
        subtitle = "Parque Central",
        time = "Hace 10 min",
        status = AlertStatus.WARNING
    ),
    ActivityItemModel(
        id = "3",
        title = "Patrulla comunitaria",
        subtitle = "Ronda finalizada",
        time = "OK",
        status = AlertStatus.SUCCESS
    )
)