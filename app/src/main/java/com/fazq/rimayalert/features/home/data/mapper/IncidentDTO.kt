package com.fazq.rimayalert.features.home.data.mapper

import com.google.gson.annotations.SerializedName


data class IncidentDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("occurred_at")
    val occurredAt: String? = null,
    @SerializedName("severity_level")
    val severityLevel: String
)