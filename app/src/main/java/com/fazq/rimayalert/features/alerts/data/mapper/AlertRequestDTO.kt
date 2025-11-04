package com.fazq.rimayalert.features.alerts.data.mapper

import com.fazq.rimayalert.core.ui.navigation.Screen
import com.fazq.rimayalert.features.alerts.domain.model.AlertModel
import com.google.gson.annotations.SerializedName

data class AlertRequestDTO(
    @SerializedName("type")
    val type: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("latitude")
    val latitude: Double? = null,

    @SerializedName("longitude")
    val longitude: Double? = null,

    @SerializedName("image")
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