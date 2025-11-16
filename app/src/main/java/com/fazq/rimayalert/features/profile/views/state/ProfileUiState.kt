package com.fazq.rimayalert.features.profile.views.state

import com.fazq.rimayalert.core.states.DialogState

data class ProfileUiState(
    val userName: String = "",
    val userEmail: String = "",
    val memberSince: String = "01/01/2024",
    val notificationsEnabled: Boolean = true,
    val communityAssigned: String = "Centro Histórico",
    val isLoading: Boolean = false,
    val dialogState: DialogState = DialogState.None,
    val shouldNavigateToLogin: Boolean = false
)