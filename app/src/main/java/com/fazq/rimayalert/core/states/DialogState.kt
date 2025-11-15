package com.fazq.rimayalert.core.states

import com.fazq.rimayalert.features.maps.ui.state.IncidentMarker

sealed class DialogState {
    data object None : DialogState()

    data class Error(
        val title: String,
        val message: String
    ) : DialogState()

    data class Success(
        val title: String,
        val message: String
    ) : DialogState()

    data class Confirmation(
        val title: String,
        val message: String,
        val onConfirm: () -> Unit
    ) : DialogState()
}

sealed class MapDialogState {
    data object None : MapDialogState()

    data class Error(
        val title: String,
        val message: String
    ) : MapDialogState()

    data class IncidentDetails(
        val incident: IncidentMarker
    ) : MapDialogState()
}