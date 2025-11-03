package com.fazq.rimayalert.features.home.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fazq.rimayalert.features.home.ui.states.HomeUiState

@Composable
fun HomeContent(
    paddingValues: PaddingValues,
    uiState: HomeUiState,
    isLoading: Boolean,
    onCreateAlertClick: () -> Unit,
    onAlertClick: (String) -> Unit,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        WeeklySummaryCard(
            alerts = uiState.weeklySummary.alerts,
            resolved = uiState.weeklySummary.resolved,
            pending = uiState.weeklySummary.pending,
            averageTime = uiState.weeklySummary.averageTime,
            lastDays = 7
        )

        Spacer(modifier = Modifier.height(16.dp))

        CreateAlertButton(
            onClick = onCreateAlertClick,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        RecentActivitySection(
            activities = uiState.recentActivities,
            onActivityClick = onAlertClick,
            onRefresh = onRefresh,
            isRefreshing = isLoading
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}