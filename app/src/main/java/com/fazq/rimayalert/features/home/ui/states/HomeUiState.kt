package com.fazq.rimayalert.features.home.ui.states

import com.fazq.rimayalert.features.home.domain.model.ActivityItemModel

data class HomeUiState(
    val userName: String = "",
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