package com.fazq.rimayalert.features.alerts.views.state

import com.fazq.rimayalert.core.states.DialogState

data class AlertUiState(
    val userName: String = "",
    val selectedType: String = "Médica",
    val description: String = "",
    val location: String = "Ubicación actual",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val imageUri: String? = null,
    val isLoading: Boolean = false,
    val dialogState: DialogState = DialogState.None
)