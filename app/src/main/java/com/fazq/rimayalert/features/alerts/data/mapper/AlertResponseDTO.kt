package com.fazq.rimayalert.features.alerts.data.mapper

import com.google.gson.annotations.SerializedName

data class AlertResponseDTO(
    @SerializedName("message")
    val message: String,
    @SerializedName("incident_id")
    val incidentId: String? = null,
)