package com.fazq.rimayalert.features.alerts.views.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.connection.location.LocationManager
import com.fazq.rimayalert.core.preferences.LocationPermissionsManager
import com.fazq.rimayalert.core.preferences.UserPreferencesManager
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.states.DialogState
import com.fazq.rimayalert.core.ui.extensions.getDisplayName
import com.fazq.rimayalert.features.alerts.domain.model.AlertModel
import com.fazq.rimayalert.features.alerts.domain.usecase.AlertUseCase
import com.fazq.rimayalert.features.alerts.views.event.AlertEvent
import com.fazq.rimayalert.features.alerts.views.state.AlertUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class AlertViewModel @Inject constructor(
    private val alertUseCase: AlertUseCase,
    private val userPreferencesManager: UserPreferencesManager,
    val locationPermissionsManager: LocationPermissionsManager,
    private val locationManager: LocationManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(AlertUiState())
    val uiState: StateFlow<AlertUiState> = _uiState.asStateFlow()


    init {
        observeUser()
        getCurrentLocation()
    }

    fun onEvent(event: AlertEvent) {
        when (event) {
            is AlertEvent.TypeSelected -> handleTypeSelected(event.type)
            is AlertEvent.DescriptionChanged -> handleDescriptionChanged(event.description)
            is AlertEvent.LocationUpdated -> handleLocationUpdated(
                event.location,
                event.latitude,
                event.longitude
            )

            is AlertEvent.ImageSelected -> handleImageSelected(event.uri)
            AlertEvent.RemoveImage -> handleRemoveImage()
            AlertEvent.LocationEdit -> handleLocationEdit()
            AlertEvent.UseMap -> handleUseMap()
            AlertEvent.SendAlert -> validateAndShowConfirmation()
            AlertEvent.DismissDialog -> dismissDialog()
            AlertEvent.ConfirmSend -> sendAlert()
            AlertEvent.DismissLocationDialog -> dismissLocationDialog()
            is AlertEvent.ConfirmLocationEdit -> confirmLocationEdit(
                event.address,
                event.latitude,
                event.longitude
            )

            AlertEvent.RefreshLocation -> getCurrentLocation()
        }
    }

    private fun observeUser() {
        viewModelScope.launch {
            userPreferencesManager.user.collect { user ->
                _uiState.update { it.copy(userName = user.getDisplayName()) }
            }
        }
    }

    private fun getCurrentLocation() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingLocation = true) }

            val result = locationManager.getCurrentLocation()

            if (result.isSuccess) {
                val locationData = result.getOrNull()
                _uiState.update {
                    it.copy(
                        location = locationData?.address ?: "Ubicación desconocida",
                        latitude = locationData?.latitude,
                        longitude = locationData?.longitude,
                        isLoadingLocation = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        location = "No se pudo obtener la ubicación",
                        latitude = null,
                        longitude = null,
                        isLoadingLocation = false
                    )
                }
            }
        }
    }

    private fun handleTypeSelected(type: String) {
        _uiState.update { it.copy(selectedType = type) }
    }

    private fun dismissLocationDialog() {
        _uiState.update {
            it.copy(showLocationEditDialog = false)
        }
    }

    private fun handleDescriptionChanged(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    private fun confirmLocationEdit(
        address: String,
        latitude: Double?,
        longitude: Double?
    ) {
        _uiState.update {
            it.copy(
                location = address,
                latitude = latitude,
                longitude = longitude,
                showLocationEditDialog = false
            )
        }
    }


    private fun handleLocationUpdated(
        location: String,
        latitude: Double?,
        longitude: Double?
    ) {
        _uiState.update {
            it.copy(
                location = location,
                latitude = latitude,
                longitude = longitude
            )
        }
    }

    private fun handleImageSelected(uri: Uri?) {
        _uiState.update { it.copy(imageUri = uri?.toString()) }
    }

    private fun handleRemoveImage() {
        _uiState.update { it.copy(imageUri = null) }
    }

    private fun handleLocationEdit() {
        // TODO: Implementar edición manual de ubicación
    }

    private fun handleUseMap() {
        // TODO: Implementar selección de ubicación en mapa
    }

    private fun validateAndShowConfirmation() {
        val currentState = _uiState.value

        when {
            currentState.description.isBlank() -> {
                _uiState.update {
                    it.copy(
                        dialogState = DialogState.Error(
                            title = "Campo requerido",
                            message = "Por favor, ingresa una descripción de la alerta"
                        )
                    )
                }
            }

            currentState.description.length < 10 -> {
                _uiState.update {
                    it.copy(
                        dialogState = DialogState.Error(
                            title = "Descripción muy corta",
                            message = "La descripción debe tener al menos 10 caracteres"
                        )
                    )
                }
            }

            else -> {
                _uiState.update {
                    it.copy(
                        dialogState = DialogState.Confirmation(
                            title = "Confirmar envío",
                            message = "¿Estás seguro de enviar esta alerta de tipo ${currentState.selectedType}?",
                            onConfirm = { onEvent(AlertEvent.ConfirmSend) }
                        )
                    )
                }
            }
        }
    }

    private fun sendAlert() {
        val currentState = _uiState.value

        val alertModel = AlertModel(
            type = currentState.selectedType,
            description = currentState.description,
            location = currentState.location,
            latitude = currentState.latitude,
            longitude = currentState.longitude,
            imageUri = currentState.imageUri
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, dialogState = DialogState.None) }
            Log.d("AlertViewModel", "sendAlert: $alertModel")

            when (val result = alertUseCase.createAlert(alertModel)) {
                is DataState.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            dialogState = DialogState.Success(
                                title = "Alerta enviada",
                                message = result.data
                            )
                        )
                    }
                }

                is DataState.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            dialogState = DialogState.Error(
                                title = "Error al enviar",
                                message = result.message
                            )
                        )
                    }
                }
            }
        }
    }

    private fun dismissDialog() {
        _uiState.update { it.copy(dialogState = DialogState.None) }
    }

    fun resetForm() {
        _uiState.value = AlertUiState()
    }

}