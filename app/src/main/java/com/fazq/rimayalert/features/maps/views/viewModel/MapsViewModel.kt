package com.fazq.rimayalert.features.maps.viewmodel

import android.R.attr.type
import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.preferences.LocationPermissionsManager
import com.fazq.rimayalert.core.preferences.UserPreferencesManager
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.states.MapDialogState
import com.fazq.rimayalert.core.ui.extensions.getDisplayName
import com.fazq.rimayalert.features.maps.domain.model.MapIncidentModel
import com.fazq.rimayalert.features.maps.domain.usecase.MapUseCase
import com.fazq.rimayalert.features.maps.views.event.MapsEvent
import com.fazq.rimayalert.features.maps.views.state.IncidentMarker
import com.fazq.rimayalert.features.maps.views.state.IncidentType
import com.fazq.rimayalert.features.maps.views.state.MapsUiState
import com.fazq.rimayalert.features.maps.views.state.Priority
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
    private val userPreferencesManager: UserPreferencesManager,
    private val locationPermissionsManager: LocationPermissionsManager,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val mapUseCase: MapUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapsUiState())
    val uiState: StateFlow<MapsUiState> = _uiState.asStateFlow()

    init {
        observeUser()
        checkLocationPermission()
    }

    fun onEvent(event: MapsEvent) {
        when (event) {
            is MapsEvent.RequestLocationPermission -> checkLocationPermission()
            is MapsEvent.GetCurrentLocation -> getCurrentLocation()
            is MapsEvent.OnMapReady -> onMapReady(event.isReady)
            is MapsEvent.OnIncidentSelected -> selectIncident(event.incidentId)
            is MapsEvent.OnMapCameraMove -> updateCameraPosition(event.position, event.zoom)
            is MapsEvent.DismissError -> dismissDialog()
            is MapsEvent.RefreshIncidents -> refreshIncidents()
            is MapsEvent.OnMyLocationClick -> getCurrentLocation()
            is MapsEvent.OnRefreshClick -> refreshIncidents()
        }
    }

    private fun observeUser() {
        viewModelScope.launch {
            userPreferencesManager.user.collect { userData ->
                _uiState.update { it.copy(userName = userData?.getDisplayName() ?: "") }
            }
        }
    }

    private fun checkLocationPermission() {
        val hasPermission = locationPermissionsManager.hasAnyLocationPermission()
        _uiState.update { it.copy(hasLocationPermission = hasPermission) }

        if (hasPermission) {
            getCurrentLocation()
            loadMapIncidents()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (!_uiState.value.hasLocationPermission) {
            _uiState.update {
                it.copy(
                    dialogState = MapDialogState.Error(
                        title = "Permiso denegado",
                        message = locationPermissionsManager.getPermissionDeniedMessage()
                    )
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
                            isLoadingLocation = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoadingLocation = false,
                            dialogState = MapDialogState.Error(
                                title = "Error de ubicación",
                                message = "No se pudo obtener la ubicación actual"
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingLocation = false,
                        dialogState = MapDialogState.Error(
                            title = "Error de ubicación",
                            message = "Error al obtener ubicación: ${e.localizedMessage}"
                        )
                    )
                }
            }
        }
    }

    private fun loadMapIncidents() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingIncidents = true) }

            when (val result = mapUseCase.getMapIncidents()) {
                is DataState.Success -> {
                    val mapData = result.data
                    _uiState.update {
                        it.copy(
                            myIncidents = mapData.myIncidents,
                            otherIncidents = mapData.otherIncidents,
                            totalCount = mapData.totalCount,
                            radiusKm = mapData.radiusKm,
                            userLocation = mapData.userLocation,
                            isLoadingIncidents = false
                        )
                    }
                }

                is DataState.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoadingIncidents = false,
                            dialogState = MapDialogState.Error(
                                title = "Error al cargar incidentes",
                                message = result.message
                            )
                        )
                    }
                }
            }
        }
    }

    private fun onMapReady(isReady: Boolean) {
        if (isReady && _uiState.value.hasLocationPermission) {
            if (_uiState.value.myIncidents.isEmpty() && _uiState.value.otherIncidents.isEmpty()) {
                loadMapIncidents()
            }
        }
    }

    private fun selectIncident(incidentId: Int?) {
        val allIncidents = _uiState.value.myIncidents + _uiState.value.otherIncidents
        val incident = incidentId?.let { id ->
            allIncidents.find { it.id == id }
        }
        _uiState.update {
            it.copy(
                selectedIncident = incident,
                dialogState = incident?.let { MapDialogState.IncidentDetails(it.toIncidentMarker()) }
                    ?: MapDialogState.None
            )
        }
    }

    fun MapIncidentModel.toIncidentMarker(): IncidentMarker {

        val lat = latitude ?: 0.0
        val lng = longitude ?: 0.0

        val mappedType = mapStringToIncidentType(incidentTypeName)

        val mappedPriority = when (severityLevel) {
            1 -> Priority.LOW
            2 -> Priority.MEDIUM
            3 -> Priority.HIGH
            else -> Priority.MEDIUM
        }

        return IncidentMarker(
            id = id.toString(),
            position = LatLng(lat, lng),
            title = title,
            type = mappedType,
            priority = mappedPriority
        )
    }

    fun mapStringToIncidentType(raw: String?): IncidentType {
        if (raw.isNullOrBlank()) return IncidentType.RESIDENCE

        return when (raw.trim().lowercase()) {

            "power_outage", "outage", "blackout", "sin_energia" ->
                IncidentType.POWER_OUTAGE

            "fire", "incendio", "fuego" ->
                IncidentType.FIRE

            "medical_emergency", "medical", "emergencia_medica" ->
                IncidentType.MEDICAL_EMERGENCY

            "residence", "hogar", "casa" ->
                IncidentType.RESIDENCE

            else ->
                IncidentType.RESIDENCE
        }
    }


    private fun updateCameraPosition(position: LatLng, zoom: Float) {
        _uiState.update { it.copy(mapZoom = zoom) }
    }

    private fun dismissDialog() {
        _uiState.update { it.copy(dialogState = MapDialogState.None) }
    }

    private fun refreshIncidents() {
        loadMapIncidents()
    }

}