package com.fazq.rimayalert.features.maps.views.component

import androidx.compose.runtime.Composable
import com.fazq.rimayalert.features.maps.domain.model.MapIncidentModel
import com.fazq.rimayalert.features.maps.views.state.IncidentMarker
import com.fazq.rimayalert.features.maps.views.state.IncidentType
import com.fazq.rimayalert.features.maps.views.state.Priority
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun IncidentMarkerComponent(
    incident: MapIncidentModel,
    isOwn: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val position = LatLng(incident.latitude ?: return, incident.longitude ?: return)

    val markerColor = when {
        isOwn -> BitmapDescriptorFactory.HUE_AZURE // Azul para mis incidentes
        else -> when (incident.severityLevel) {
            3 -> BitmapDescriptorFactory.HUE_RED    // Alta
            2 -> BitmapDescriptorFactory.HUE_ORANGE // Media
            1 -> BitmapDescriptorFactory.HUE_YELLOW // Baja
            else -> BitmapDescriptorFactory.HUE_VIOLET
        }
    }

    Marker(
        state = MarkerState(position = position),
        title = incident.title,
        snippet = "${incident.incidentTypeName} - ${incident.statusName}",
        icon = BitmapDescriptorFactory.defaultMarker(markerColor),
        alpha = if (isSelected) 1f else 0.85f,
        onClick = {
            onClick()
            true
        }
    )
}