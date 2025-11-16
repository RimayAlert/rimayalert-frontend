package com.fazq.rimayalert.features.profile.views.state

import com.fazq.rimayalert.core.states.DialogState

data class ProfileUiState(
    val userName: String = "",
    val dialogState: DialogState = DialogState.None
)