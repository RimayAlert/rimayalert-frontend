package com.fazq.rimayalert.features.profile.views.state

import com.fazq.rimayalert.core.states.DialogState

data class ProfileUiState(
    val userName: String = "",
    val userEmail: String = "",
    val memberSince: String = "27/11/2025",
    val notificationsEnabled: Boolean = true,
    val communityAssigned: String = "Milagro",
    val isLoading: Boolean = false,
    val dialogState: DialogState = DialogState.None,
    val shouldNavigateToLogin: Boolean = false
)