package com.fazq.rimayalert.features.home.domain.model

import com.fazq.rimayalert.features.home.data.mapper.IncidentDTO

data class IncidentModel(
    val id: Int,
    val title: String,
    val description: String,
    val occurredAt: String?,
    val severityLevel: String?
)

fun IncidentDTO.toDomain() = IncidentModel(
    id,
    title,
    description,
    occurredAt,
    severityLevel
)