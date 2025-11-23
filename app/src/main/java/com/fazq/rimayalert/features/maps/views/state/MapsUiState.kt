package com.fazq.rimayalert.features.maps.views.state

import com.fazq.rimayalert.core.states.MapDialogState
import com.fazq.rimayalert.features.maps.domain.model.MapIncidentModel
import com.fazq.rimayalert.features.maps.domain.model.UserLocationModel
import com.google.android.gms.maps.model.LatLng

data class MapsUiState(
    val userName: String? = null,
    val currentLocation: LatLng? = null,
    val isLoadingLocation: Boolean = false,
    val isLoadingIncidents: Boolean = false,
    val hasLocationPermission: Boolean = false,

    val myIncidents: List<MapIncidentModel> = emptyList(),
    val otherIncidents: List<MapIncidentModel> = emptyList(),
    val totalCount: Int = 0,
    val radiusKm: Double = 5.0,
    val userLocation: UserLocationModel? = null,

    val selectedIncident: MapIncidentModel? = null,
    val mapZoom: Float = 15f,
    val dialogState: MapDialogState = MapDialogState.None
)

data class IncidentMarker(
    val id: String,
    val position: LatLng,
    val title: String,
    val type: IncidentType,
    val priority: Priority
)

enum class IncidentType {
    POWER_OUTAGE,
    FIRE,
    MEDICAL_EMERGENCY,
    RESIDENCE
}

enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}