package com.fazq.rimayalert.features.alerts.ui.event

import android.net.Uri

sealed class AlertEvent {
    data class TypeSelected(val type: String) : AlertEvent()
    data class DescriptionChanged(val description: String) : AlertEvent()
    data class LocationUpdated(
        val location: String,
        val latitude: Double? = null,
        val longitude: Double? = null
    ) : AlertEvent()
    data class ImageSelected(val uri: Uri?) : AlertEvent()
    data object RemoveImage : AlertEvent()
    data object LocationEdit : AlertEvent()
    data object UseMap : AlertEvent()
    data object SendAlert : AlertEvent()
    data object DismissDialog : AlertEvent()
    data object ConfirmSend : AlertEvent()
}