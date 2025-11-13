package com.fazq.rimayalert.features.maps.ui.event

import com.google.android.gms.maps.model.LatLng

sealed interface MapsEvent {
    data object RequestLocationPermission : MapsEvent
    data object GetCurrentLocation : MapsEvent
    data class OnMapReady(val isReady: Boolean) : MapsEvent
    data class OnIncidentSelected(val incidentId: String?) : MapsEvent
    data class OnMapCameraMove(val position: LatLng, val zoom: Float) : MapsEvent
    data object ClearError : MapsEvent
    data object RefreshIncidents : MapsEvent
}