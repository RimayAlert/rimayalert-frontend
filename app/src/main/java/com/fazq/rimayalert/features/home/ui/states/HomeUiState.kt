package com.fazq.rimayalert.features.home.ui.states

import com.fazq.rimayalert.features.home.domain.model.IncidentModel

data class HomeUiState(
    val userName: String = "",
    val weeklySummary: WeeklySummaryUiState = WeeklySummaryUiState(),
    val recentActivities: List<IncidentModel> = emptyList(),
    val isRefreshing: Boolean = false,
    val hasCommunity: Boolean = false,
    val communityName: String? = null
)

data class WeeklySummaryUiState(
    val alerts: Int = 0,
    val resolved: Int = 0,
    val pending: Int = 0,
    val averageTime: String = "0h 0m"
)