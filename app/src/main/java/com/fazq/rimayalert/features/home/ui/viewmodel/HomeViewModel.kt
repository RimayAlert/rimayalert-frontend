package com.fazq.rimayalert.features.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.preferences.LocationPermissionsManager
import com.fazq.rimayalert.core.preferences.PermissionsManager
import com.fazq.rimayalert.core.preferences.UserPreferencesManager
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.auth.domain.model.UserModel
import com.fazq.rimayalert.features.auth.domain.usecase.AuthUseCase
import com.fazq.rimayalert.features.home.domain.usecase.CommunityUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val authUseCase: AuthUseCase,
    private val communityUseCase: CommunityUseCase,
    val permissionsManager: PermissionsManager,
    private val locationPermissionsManager: LocationPermissionsManager,
    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<BaseUiState>(BaseUiState.EmptyState)
    val homeUiState: StateFlow<BaseUiState> = _homeUiState.asStateFlow()

    private val _checkCommunityState = MutableStateFlow<BaseUiState>(BaseUiState.EmptyState)
    val checkCommunityState: StateFlow<BaseUiState> = _checkCommunityState.asStateFlow()

    private val _assignCommunityState = MutableStateFlow<BaseUiState>(BaseUiState.EmptyState)
    val assignCommunityState: StateFlow<BaseUiState> = _assignCommunityState.asStateFlow()

    val isCameraGranted = permissionsManager.isCameraPermissionGranted
    val isStorageGranted = permissionsManager.isStoragePermissionGranted
    val isCameraDeniedPermanently = permissionsManager.isCameraPermissionDeniedPermanently
    val isStorageDeniedPermanently = permissionsManager.isStoragePermissionDeniedPermanently

    val isFineLocationGranted = locationPermissionsManager.isFineLocationGranted
    val isCoarseLocationGranted = locationPermissionsManager.isCoarseLocationGranted
    val isLocationDeniedPermanently = locationPermissionsManager.isLocationDeniedPermanently
    val wasLocationPermissionRequested = locationPermissionsManager.wasLocationPermissionRequested

    val user: StateFlow<UserModel?> = userPreferencesManager.user
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    init {
        checkInitialCommunityStatus()
    }

    private fun checkInitialCommunityStatus() {
        viewModelScope.launch {
            locationPermissionsManager.syncPermissionsWithSystem()

            if (!locationPermissionsManager.hasAnyLocationPermission()) {
                return@launch
            }

            checkCommunityStatus()
        }
    }

    fun checkCommunityStatus() {
        viewModelScope.launch {
            _checkCommunityState.value = BaseUiState.LoadingState
            when (val responseState = communityUseCase.checkCommunityStatus()) {
                is DataState.Success -> {
                    _checkCommunityState.value = BaseUiState.SuccessState(responseState.data)
                }

                is DataState.Error -> {
                    _checkCommunityState.value = BaseUiState.ErrorState(responseState.message)
                }
            }
        }
    }

    fun assignCommunityWithCurrentLocation() {
        viewModelScope.launch {
            try {
                _assignCommunityState.value = BaseUiState.LoadingState

                if (!locationPermissionsManager.hasAnyLocationPermission()) {
                    _assignCommunityState.value = BaseUiState.ErrorState(
                        locationPermissionsManager.getPermissionDeniedMessage()
                    )
                    return@launch
                }

                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        viewModelScope.launch {
                            assignCommunity(location.latitude, location.longitude)
                        }
                    } else {
                        _assignCommunityState.value =
                            BaseUiState.ErrorState("No se pudo obtener la ubicación")
                    }
                }.addOnFailureListener {
                    _assignCommunityState.value =
                        BaseUiState.ErrorState("Error al obtener ubicación: ${it.message}")
                }

            } catch (e: Exception) {
                _assignCommunityState.value = BaseUiState.ErrorState("Error: ${e.message}")
            }
        }
    }

    private suspend fun assignCommunity(latitude: Double, longitude: Double) {
        when (val responseState = communityUseCase.assignCommunity(latitude, longitude)) {
            is DataState.Success -> {
                _assignCommunityState.value = BaseUiState.SuccessState(responseState.data)
            }

            is DataState.Error -> {
                _assignCommunityState.value = BaseUiState.ErrorState(responseState.message)
            }
        }
    }

    fun handleLocationPermissionsResult(
        permissions: Map<String, Boolean>,
        shouldShowRationale: Boolean
    ) {
        viewModelScope.launch {
            locationPermissionsManager.handleLocationPermissionsResult(
                permissions,
                shouldShowRationale
            )

            if (locationPermissionsManager.hasAnyLocationPermission()) {
                assignCommunityWithCurrentLocation()
            }
        }
    }

    fun hasLocationPermission(): Boolean {
        return locationPermissionsManager.hasAnyLocationPermission()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _homeUiState.value = BaseUiState.LoadingState
            // TODO: Cargar datos reales del home
            _homeUiState.value = BaseUiState.SuccessState(data = "Home Data Loaded")
        }
    }

    fun resetCheckCommunityState() {
        _checkCommunityState.value = BaseUiState.EmptyState
    }

    fun resetAssignCommunityState() {
        _assignCommunityState.value = BaseUiState.EmptyState
    }

    fun resetState() {
        _homeUiState.value = BaseUiState.EmptyState
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            // authUseCase.logout()
            onLogoutSuccess()
        }
    }
}