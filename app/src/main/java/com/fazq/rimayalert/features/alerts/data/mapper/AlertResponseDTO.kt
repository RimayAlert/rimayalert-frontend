package com.fazq.rimayalert.features.alerts.data.mapper

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlertResponseDTO(
    @SerialName("message")
    val message: String,

    @SerialName("incident_id")
    val incidentId: String? = null,
)