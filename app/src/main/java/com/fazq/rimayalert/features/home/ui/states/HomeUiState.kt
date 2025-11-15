package com.fazq.rimayalert.features.home.ui.states

import com.fazq.rimayalert.core.states.DialogState
import com.fazq.rimayalert.features.auth.domain.model.UserModel
import com.fazq.rimayalert.features.home.domain.model.IncidentModel

data class HomeUiState(
    val userName: String = "",
    val user: UserModel? = null,
    val hasCommunity: Boolean = false,
    val communityName: String? = null,
    val communityCheckCompleted: Boolean = false,
    val recentActivities: List<IncidentModel> = emptyList(),
    val isRefreshing: Boolean = false,
    val isLoadingHome: Boolean = false,
    val isLoadingCommunity: Boolean = false,
    val dialogState: DialogState = DialogState.None
)