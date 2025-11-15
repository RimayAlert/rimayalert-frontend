package com.fazq.rimayalert.features.maps.ui.state

import com.google.android.gms.maps.model.LatLng

data class MapsUiState(
    val currentLocation: LatLng? = null,
    val isLoadingLocation: Boolean = false,
    val hasLocationPermission: Boolean = false,
    val errorMessage: String? = null,
    val incidents: List<IncidentMarker> = emptyList(),
    val selectedIncident: IncidentMarker? = null,
    val mapZoom: Float = 15f
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