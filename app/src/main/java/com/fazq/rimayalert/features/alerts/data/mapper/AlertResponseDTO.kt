package com.fazq.rimayalert.features.alerts.data.mapper

import com.google.gson.annotations.SerializedName

data class AlertResponseDTO(
    @SerializedName("message")
    val message: String,

    @SerializedName("alert_id")
    val alertId: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null
)