package com.fazq.rimayalert.features.alerts.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.room.util.copy
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.features.alerts.ui.state.AlertUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


@HiltViewModel
class AlertViewModel @Inject constructor(
) : ViewModel() {
    private val _alertUiState = MutableStateFlow(AlertUiState())
    val alertUiState: StateFlow<AlertUiState> = _alertUiState.asStateFlow()

    fun onTypeSelected(type: String) {
        _alertUiState.update { it.copy(selectedType = type) }
    }

    fun onDescriptionChanged(desc: String) {
        _alertUiState.update { it.copy(description = desc) }
    }

    fun onUploadImage() {
        // Lógica para subir imagen o abrir cámara
    }

    fun sendAlert() {
        // TODO: implementar envío de alerta al backend
    }

    fun onOpenCamera() {
        // Lógica para abrir cámara
    }
    fun onLocationEdit(){

    }
    fun onUseMap(){

    }

}