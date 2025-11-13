package com.fazq.rimayalert.features.maps.ui.component

import androidx.compose.runtime.Composable
import com.fazq.rimayalert.features.maps.ui.state.IncidentMarker
import com.fazq.rimayalert.features.maps.ui.state.IncidentType
import com.fazq.rimayalert.features.maps.ui.state.Priority
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun IncidentMarkerComponent(
    incident: IncidentMarker,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val markerColor = when (incident.priority) {
        Priority.LOW -> BitmapDescriptorFactory.HUE_BLUE
        Priority.MEDIUM -> BitmapDescriptorFactory.HUE_YELLOW
        Priority.HIGH -> BitmapDescriptorFactory.HUE_RED
    }

    Marker(
        state = MarkerState(position = incident.position),
        title = incident.title,
        snippet = getIncidentTypeLabel(incident.type),
        icon = BitmapDescriptorFactory.defaultMarker(markerColor),
        alpha = if (isSelected) 1f else 0.8f,
        onClick = {
            onClick()
            true
        }
    )
}

private fun getIncidentTypeLabel(type: IncidentType): String {
    return when (type) {
        IncidentType.POWER_OUTAGE -> "Corte eléctrico"
        IncidentType.FIRE -> "Incendio"
        IncidentType.MEDICAL_EMERGENCY -> "Emergencia médica"
        IncidentType.RESIDENCE -> "Zona de residencia"
    }
}
