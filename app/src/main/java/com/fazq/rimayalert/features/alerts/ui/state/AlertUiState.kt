package com.fazq.rimayalert.features.alerts.ui.state

data class AlertUiState(
    val selectedType: String = "Médica",
    val location: String = "Edif. A - 2°B",
    val description: String = "",
    val imageUri: String? = null
)