package com.fazq.rimayalert.features.home.data.mapper

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IncidentDTO(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String,

    @SerialName("occurred_at")
    val occurredAt: String? = null,

    @SerialName("severity_level")
    val severityLevel: String
)