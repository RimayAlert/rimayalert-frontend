package com.fazq.rimayalert.features.profile.views.state

data class ProfileUiState(
    val userName: String = "",
    val userEmail: String = "",
    val memberSince: String = "01/01/2024",
    val notificationsEnabled: Boolean = true,
    val communityAssigned: String = "Centro Histórico",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)