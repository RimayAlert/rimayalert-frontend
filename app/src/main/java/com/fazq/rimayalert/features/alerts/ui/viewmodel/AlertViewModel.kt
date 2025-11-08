package com.fazq.rimayalert.features.alerts.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.alerts.domain.model.AlertModel
import com.fazq.rimayalert.features.alerts.domain.usecase.AlertUseCase
import com.fazq.rimayalert.features.alerts.ui.state.AlertUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class AlertViewModel @Inject constructor(
    private val alertUseCase: AlertUseCase
) : ViewModel() {
    private val _alertUiState = MutableStateFlow(AlertUiState())
    val alertUiState: StateFlow<AlertUiState> = _alertUiState.asStateFlow()

    private val _sendAlertState = MutableStateFlow<BaseUiState>(BaseUiState.EmptyState)
    val sendAlertState: StateFlow<BaseUiState> = _sendAlertState.asStateFlow()


    fun onTypeSelected(type: String) {
        _alertUiState.update { it.copy(selectedType = type) }
    }

    fun onDescriptionChanged(desc: String) {
        _alertUiState.update { it.copy(description = desc) }
    }

    fun onLocationEdit() {

    }

    fun onUseMap() {

    }

    fun updateLocation(location: String, latitude: Double? = null, longitude: Double? = null) {
        _alertUiState.value = _alertUiState.value.copy(
            location = location,
            latitude = latitude,
            longitude = longitude
        )
    }

    fun updateImageUri(uri: Uri?) {
        _alertUiState.value = _alertUiState.value.copy(imageUri = uri?.toString())
    }

    fun removeImage() {
        _alertUiState.update { it.copy(imageUri = null) }
    }


    fun sendAlert() {
        val currentState = _alertUiState.value

        if (currentState.description.isBlank()) {
            _sendAlertState.value = BaseUiState.ErrorState("Por favor, ingresa una descripción")
            return
        }

        if (currentState.description.length < 10) {
            _sendAlertState.value =
                BaseUiState.ErrorState("La descripción debe tener al menos 10 caracteres")
            return
        }
        val alertModel = AlertModel(
            type = currentState.selectedType,
            description = currentState.description,
            location = currentState.location,
            latitude = currentState.latitude,
            longitude = currentState.longitude,
            imageUri = currentState.imageUri
        )
        viewModelScope.launch {
            _sendAlertState.value = BaseUiState.LoadingState
            _alertUiState.value = _alertUiState.value.copy(isLoading = true)

            when (val responseState = alertUseCase.createAlert(alertModel)) {
                is DataState.Success -> {
                    _sendAlertState.value = BaseUiState.SuccessState(responseState.data)
                    _alertUiState.value = _alertUiState.value.copy(
                        isLoading = false,
                        success = responseState.data
                    )
                    resetForm()
                }

                is DataState.Error -> {
                    _sendAlertState.value = BaseUiState.ErrorState(responseState.message)
                    _alertUiState.value = _alertUiState.value.copy(
                        isLoading = false,
                        error = responseState.message
                    )
                }
            }
        }
    }

    fun resetState() {
        _sendAlertState.value = BaseUiState.EmptyState
        _alertUiState.value = _alertUiState.value.copy(
            error = null,
            success = null,
            isLoading = false
        )
    }

    private fun resetForm() {
        _alertUiState.value = AlertUiState()
    }


}