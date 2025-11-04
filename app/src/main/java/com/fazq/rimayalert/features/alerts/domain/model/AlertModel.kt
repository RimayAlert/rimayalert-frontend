package com.fazq.rimayalert.features.alerts.domain.model

import com.fazq.rimayalert.features.alerts.data.mapper.AlertRequestDTO

data class AlertModel(
    val type: String,
    val description: String,
    val location: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val imageUri: String? = null
)

fun AlertModel.toRequestDTO() = AlertRequestDTO(
    type = type,
    description = description,
    location = location,
    latitude = latitude,
    longitude = longitude,
    image = imageUri
)