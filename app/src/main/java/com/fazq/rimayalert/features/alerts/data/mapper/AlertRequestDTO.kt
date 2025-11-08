package com.fazq.rimayalert.features.alerts.data.mapper

import com.fazq.rimayalert.core.ui.navigation.Screen
import com.fazq.rimayalert.features.alerts.domain.model.AlertModel
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlertRequestDTO(
    @SerialName("type")
    val type: String,

    @SerialName("description")
    val description: String,

    @SerialName("location")
    val location: String,

    @SerialName("latitude")
    val latitude: Double? = null,

    @SerialName("longitude")
    val longitude: Double? = null,

    @SerialName("image")
    val image: String? = null
)

fun AlertRequestDTO.toDomain() = AlertModel(
    type = type,
    description = description,
    location = location,
    latitude = latitude,
    longitude = longitude,
    imageUri = image
)