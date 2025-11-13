package com.fazq.rimayalert.features.maps.ui.component


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.fazq.rimayalert.features.maps.ui.state.IncidentMarker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.internal.IGoogleMapDelegate
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapView(
    currentLocation: LatLng?,
    incidents: List<IncidentMarker>,
    selectedIncidentId: String?,
    hasLocationPermission: Boolean,
    onIncidentClick: (String) -> Unit,
    onMapReady: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val defaultLocation = LatLng(-2.1894, -79.8886) // Milagro, Ecuador
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation ?: defaultLocation, 15f)
    }

    var isMapLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(currentLocation) {
        currentLocation?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(it, 15f),
                durationMs = 1000
            )
        }
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = hasLocationPermission
        ),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = hasLocationPermission,
            compassEnabled = true
        ),
        onMapLoaded = {
            isMapLoaded = true
            onMapReady(true)
        }
    ) {
        currentLocation?.let {
            Marker(
                state = MarkerState(position = it),
                title = "Mi ubicación",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
            )
        }

        incidents.forEach { incident ->
            IncidentMarkerComponent(
                incident = incident,
                isSelected = incident.id == selectedIncidentId,
                onClick = { onIncidentClick(incident.id) }
            )
        }
    }
}