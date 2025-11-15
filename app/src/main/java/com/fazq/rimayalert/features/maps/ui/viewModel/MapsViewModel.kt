package com.fazq.rimayalert.features.maps.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.preferences.LocationPermissionsManager
import com.fazq.rimayalert.features.maps.ui.event.MapsEvent
import com.fazq.rimayalert.features.maps.ui.state.IncidentMarker
import com.fazq.rimayalert.features.maps.ui.state.IncidentType
import com.fazq.rimayalert.features.maps.ui.state.MapsUiState
import com.fazq.rimayalert.features.maps.ui.state.Priority
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.google.android.gms.location.Priority as LocationPriority


@HiltViewModel
class MapsViewModel @Inject constructor(
    private val locationPermissionsManager: LocationPermissionsManager,
    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapsUiState())
    val uiState: StateFlow<MapsUiState> = _uiState.asStateFlow()

    init {
        checkLocationPermission()
        loadMockIncidents() // TODO : Temporal: cargar incidentes de prueba
    }

    fun onEvent(event: MapsEvent) {
        when (event) {
            is MapsEvent.RequestLocationPermission -> checkLocationPermission()
            is MapsEvent.GetCurrentLocation -> getCurrentLocation()
            is MapsEvent.OnMapReady -> onMapReady(event.isReady)
            is MapsEvent.OnIncidentSelected -> selectIncident(event.incidentId)
            is MapsEvent.OnMapCameraMove -> updateCameraPosition(event.position, event.zoom)
            is MapsEvent.ClearError -> clearError()
            is MapsEvent.RefreshIncidents -> refreshIncidents()
        }
    }

    private fun checkLocationPermission() {
        val hasPermission = locationPermissionsManager.hasAnyLocationPermission()
        _uiState.update { it.copy(hasLocationPermission = hasPermission) }

        if (hasPermission) {
            getCurrentLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (!_uiState.value.hasLocationPermission) {
            _uiState.update {
                it.copy(
                    errorMessage = locationPermissionsManager.getPermissionDeniedMessage()
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingLocation = true) }

            try {
                val location = fusedLocationClient
                    .getCurrentLocation(LocationPriority.PRIORITY_HIGH_ACCURACY, null)
                    .await()

                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    _uiState.update {
                        it.copy(
                            currentLocation = latLng,
                            isLoadingLocation = false,
                            errorMessage = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoadingLocation = false,
                            errorMessage = "No se pudo obtener la ubicación"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingLocation = false,
                        errorMessage = "Error al obtener ubicación: ${e.localizedMessage}"
                    )
                }
            }
        }
    }

    private fun onMapReady(isReady: Boolean) {
        if (isReady && _uiState.value.hasLocationPermission) {
            getCurrentLocation()
        }
    }

    private fun selectIncident(incidentId: String?) {
        val incident = incidentId?.let { id ->
            _uiState.value.incidents.find { it.id == id }
        }
        _uiState.update { it.copy(selectedIncident = incident) }
    }

    private fun updateCameraPosition(position: LatLng, zoom: Float) {
        _uiState.update { it.copy(mapZoom = zoom) }
    }

    private fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    private fun refreshIncidents() {
        // TODO: Implementar llamada al caso de uso para obtener incidentes reales
        loadMockIncidents()
    }

    // Temporal: datos de prueba basados en tu diseño
    private fun loadMockIncidents() {
        val mockIncidents = listOf(
            IncidentMarker(
                id = "1",
                position = LatLng(37.7749, -122.4194), // Ajusta coordenadas
                title = "Corte eléctrico",
                type = IncidentType.POWER_OUTAGE,
                priority = Priority.MEDIUM
            ),
            IncidentMarker(
                id = "2",
                position = LatLng(37.7849, -122.4094),
                title = "Incendio leve",
                type = IncidentType.FIRE,
                priority = Priority.HIGH
            ),
            IncidentMarker(
                id = "3",
                position = LatLng(37.7649, -122.4294),
                title = "Emergencia médica",
                type = IncidentType.MEDICAL_EMERGENCY,
                priority = Priority.HIGH
            ),
            IncidentMarker(
                id = "4",
                position = LatLng(37.7549, -122.4394),
                title = "Zona de residencia",
                type = IncidentType.RESIDENCE,
                priority = Priority.LOW
            )
        )

        _uiState.update { it.copy(incidents = mockIncidents) }
    }
}