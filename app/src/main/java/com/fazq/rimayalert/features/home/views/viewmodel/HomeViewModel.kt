package com.fazq.rimayalert.features.home.views.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.preferences.LocationPermissionsManager
import com.fazq.rimayalert.core.preferences.PermissionsManager
import com.fazq.rimayalert.core.preferences.UserPreferencesManager
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.states.DialogState
import com.fazq.rimayalert.core.ui.extensions.getDisplayName
import com.fazq.rimayalert.features.home.domain.usecase.CommunityUseCase
import com.fazq.rimayalert.features.home.domain.usecase.HomeUseCase
import com.fazq.rimayalert.features.home.domain.usecase.UserStatsUseCase
import com.fazq.rimayalert.features.home.views.event.HomeEvent
import com.fazq.rimayalert.features.home.views.states.HomeUiState
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val communityUseCase: CommunityUseCase,
    private val homeUseCase: HomeUseCase,
    private val userStatsUseCase: UserStatsUseCase,
    val permissionsManager: PermissionsManager,
    private val locationPermissionsManager: LocationPermissionsManager,
    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState: StateFlow<HomeUiState> = _homeState.asStateFlow()

    val isCameraGranted = permissionsManager.isCameraPermissionGranted
    val isStorageGranted = permissionsManager.isStoragePermissionGranted
    val isCameraDeniedPermanently = permissionsManager.isCameraPermissionDeniedPermanently
    val isStorageDeniedPermanently = permissionsManager.isStoragePermissionDeniedPermanently

    val isFineLocationGranted = locationPermissionsManager.isFineLocationGranted
    val isCoarseLocationGranted = locationPermissionsManager.isCoarseLocationGranted
    val isLocationDeniedPermanently = locationPermissionsManager.isLocationDeniedPermanently
    val wasLocationPermissionRequested = locationPermissionsManager.wasLocationPermissionRequested

    init {
        observeUser()
//        observeCachedCommunityStatus()
        loadHomeData()
        loadUserStatsData()
    }

    private fun observeUser() {
        viewModelScope.launch {
            userPreferencesManager.user.collect { userData ->
                _homeState.update { currentState ->
                    currentState.copy(
                        user = userData,
                        userName = userData?.getDisplayName() ?: ""
                    )
                }
            }
        }
    }

    private fun observeCachedCommunityStatus() {
        viewModelScope.launch {
            userPreferencesManager.hasCommunity.collect { hasCommunity ->
                _homeState.update {
                    it.copy(
                        hasCommunity = hasCommunity,
                        communityCheckCompleted = hasCommunity
                    )
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ValidateOrAssignCommunity -> validateOrAssignCommunity()
        }
    }

    @SuppressLint("MissingPermission")
    private fun validateOrAssignCommunity() {
        viewModelScope.launch {
            try {
                if (userPreferencesManager.hasCommunity.first()) {
                    _homeState.update { it.copy(communityCheckCompleted = true) }
                    return@launch
                }
                _homeState.update { it.copy(isLoadingCommunity = true) }

                if (!locationPermissionsManager.hasAnyLocationPermission()) {
                    _homeState.update {
                        it.copy(
                            isLoadingCommunity = false,
                            dialogState = DialogState.Error(
                                title = "Permisos requeridos",
                                message = locationPermissionsManager.getPermissionDeniedMessage()
                            )
                        )
                    }
                    return@launch
                }

                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        viewModelScope.launch {
                            when (val response = communityUseCase.validateOrAssignCommunity(
                                location.latitude,
                                location.longitude
                            )) {
                                is DataState.Success -> {
                                    _homeState.update {
                                        it.copy(
                                            isLoadingCommunity = false,
                                            communityName = response.data.community?.name,
                                            communityCheckCompleted = true,
                                            dialogState = if (response.data.message != null) {
                                                DialogState.Success(
                                                    title = "¡Comunidad asignada!",
                                                    message = response.data.message
                                                )
                                            } else {
                                                DialogState.None
                                            }
                                        )
                                    }
                                }

                                is DataState.Error -> {
                                    _homeState.update {
                                        it.copy(
                                            isLoadingCommunity = false,
                                            dialogState = DialogState.Error(
                                                title = "Error al validar comunidad",
                                                message = response.message
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        _homeState.update {
                            it.copy(
                                isLoadingCommunity = false,
                                dialogState = DialogState.Error(
                                    title = "Error de ubicación",
                                    message = "No se pudo obtener la ubicación"
                                )
                            )
                        }
                    }
                }.addOnFailureListener { exception ->
                    _homeState.update {
                        it.copy(
                            isLoadingCommunity = false,
                            dialogState = DialogState.Error(
                                title = "Error de ubicación",
                                message = "Error al obtener ubicación: ${exception.message}"
                            )
                        )
                    }
                }

            } catch (e: Exception) {
                _homeState.update {
                    it.copy(
                        isLoadingCommunity = false,
                        dialogState = DialogState.Error(
                            title = "Error",
                            message = e.message ?: "Error desconocido"
                        )
                    )
                }
            }
        }
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _homeState.update { it.copy(isLoadingHome = true) }

            when (val response = homeUseCase()) {
                is DataState.Success -> {
                    _homeState.update {
                        it.copy(
                            isLoadingHome = false,
                            recentActivities = response.data,
                            isRefreshing = false,
                        )
                    }
                }

                is DataState.Error -> {
                    _homeState.update {
                        it.copy(
                            isLoadingHome = false,
                            isRefreshing = false,
                            dialogState = DialogState.Error(
                                title = "Error al cargar datos",
                                message = response.message
                            )
                        )
                    }
                }
            }
        }
    }

    fun loadUserStatsData() {
        viewModelScope.launch {
            _homeState.update { it.copy(isLoadingHome = true) }
            val response = userStatsUseCase.invoke()
            when (response) {
                is DataState.Success -> {
                    val stats = response.data
                    _homeState.update {
                        it.copy(
                            userStats = stats,
                            isLoadingHome = false
                        )
                    }
                }

                is DataState.Error -> {
                    _homeState.update {
                        it.copy(
                            isLoadingHome = false
                        )
                    }
                }
            }
        }
    }

    fun hasLocationPermission(): Boolean {
        return locationPermissionsManager.hasAnyLocationPermission()
    }

    fun dismissDialog() {
        _homeState.update { it.copy(dialogState = DialogState.None) }
    }
}