package com.fazq.rimayalert.features.maps.views.component


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.fazq.rimayalert.features.maps.domain.model.MapIncidentModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapView(
    currentLocation: LatLng?,
    myIncidents: List<MapIncidentModel>,
    otherIncidents: List<MapIncidentModel>,
    selectedIncidentId: Int?,
    hasLocationPermission: Boolean,
    radiusKm: Double,
    onIncidentClick: (Int) -> Unit,
    onMapReady: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val defaultLocation = LatLng(-2.1894, -79.8886)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation ?: defaultLocation, 13f)
    }

    var isMapLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(currentLocation) {
        currentLocation?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(it, 13f),
                durationMs = 1000
            )
        }
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = false
        ),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = false,
            compassEnabled = true,
            mapToolbarEnabled = false
        ),
        onMapLoaded = {
            isMapLoaded = true
            onMapReady(true)
        }
    ) {
        // Círculo de radio
        currentLocation?.let { location ->
            Circle(
                center = location,
                radius = radiusKm * 1000,
                fillColor = Color(0x226366F1),
                strokeColor = Color(0xFF6366F1),
                strokeWidth = 3f
            )

            // Marker de ubicación actual mejorado
            CurrentLocationMarker(location = location)
        }

        // Markers de mis incidentes
        myIncidents.forEach { incident ->
            if (incident.latitude != null && incident.longitude != null) {
                IncidentMarkerComponent(
                    incident = incident,
                    isOwn = true,
                    isSelected = incident.id == selectedIncidentId,
                    onClick = { onIncidentClick(incident.id) }
                )
            }
        }

        // Markers de otros incidentes
        otherIncidents.forEach { incident ->
            if (incident.latitude != null && incident.longitude != null) {
                IncidentMarkerComponent(
                    incident = incident,
                    isOwn = false,
                    isSelected = incident.id == selectedIncidentId,
                    onClick = { onIncidentClick(incident.id) }
                )
            }
        }
    }
}