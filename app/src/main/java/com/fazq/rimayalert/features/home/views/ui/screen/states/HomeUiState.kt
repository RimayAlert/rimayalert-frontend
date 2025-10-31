package com.fazq.rimayalert.features.home.views.ui.screen.states

import com.fazq.rimayalert.features.home.domain.model.ActivityItemModel
import com.fazq.rimayalert.features.home.domain.model.AlertStatusModel

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