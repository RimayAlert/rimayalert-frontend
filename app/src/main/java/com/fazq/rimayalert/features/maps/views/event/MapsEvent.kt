package com.fazq.rimayalert.features.maps.views.event

import com.google.android.gms.maps.model.LatLng

sealed interface MapsEvent {
    data object RequestLocationPermission : MapsEvent
    data object GetCurrentLocation : MapsEvent
    data object RefreshIncidents : MapsEvent
    data object DismissError : MapsEvent

    data class OnMapReady(val isReady: Boolean) : MapsEvent
    data class OnIncidentSelected(val incidentId: String?) : MapsEvent
    data class OnMapCameraMove(val position: LatLng, val zoom: Float) : MapsEvent
    data object OnMyLocationClick : MapsEvent
    data object OnRefreshClick : MapsEvent
}