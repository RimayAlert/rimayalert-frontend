package com.fazq.rimayalert.features.home.views.components.sections

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
import com.fazq.rimayalert.features.home.views.components.buttons.CreateAlertButtonComponent
import com.fazq.rimayalert.features.home.views.components.cards.WeeklySummaryCardComponent
import com.fazq.rimayalert.features.home.views.states.HomeUiState

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

        WeeklySummaryCardComponent(
            alerts = uiState.userStats?.totalAlert?.toInt() ?: 0,
            resolved = uiState.userStats?.totalAlertResolved?.toInt() ?: 0,
            pending = uiState.userStats?.totalAlertPending?.toInt() ?: 0,
            topType = uiState.userStats?.topTypes,
            lastDays = 7
        )

        Spacer(modifier = Modifier.height(16.dp))

        CreateAlertButtonComponent(
            onClick = onCreateAlertClick,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        RecentActivitySectionComponent(
            activities = uiState.recentActivities,
            onActivityClick = onAlertClick,
            onRefresh = onRefresh,
            isRefreshing = isLoading
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}