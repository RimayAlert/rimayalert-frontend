package com.fazq.rimayalert.features.maps.views.component

import androidx.compose.runtime.Composable
import com.fazq.rimayalert.core.states.MapDialogState
import com.fazq.rimayalert.core.ui.components.dialogs.ErrorDialogComponent
import com.fazq.rimayalert.features.maps.views.event.MapsEvent
import com.fazq.rimayalert.features.maps.views.state.IncidentMarker

@Composable
fun HandleMapDialogs(
    dialogState: MapDialogState,
    onEvent: (MapsEvent) -> Unit
) {
    when (dialogState) {
        is MapDialogState.Error -> {
            ErrorDialogComponent(
                openDialog = true,
                title = dialogState.title,
                message = dialogState.message,
                onDismiss = { onEvent(MapsEvent.DismissError) }
            )
        }

        is MapDialogState.IncidentDetails -> {
            IncidentDetailsDialog(
                incident = dialogState.incident,
                onDismiss = { onEvent(MapsEvent.DismissError) }
            )
        }

        MapDialogState.None -> {}
    }
}

@Composable
fun IncidentDetailsDialog(incident: IncidentMarker, onDismiss: () -> Unit) {
    TODO("Not yet implemented")
}