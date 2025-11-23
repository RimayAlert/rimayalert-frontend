package com.fazq.rimayalert.features.maps.data.mapper

import com.google.gson.annotations.SerializedName

data class MapIncidentDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("latitude")
    val latitude: Double?,

    @SerializedName("longitude")
    val longitude: Double?,

    @SerializedName("severity_level")
    val severityLevel: Int?,

    @SerializedName("incident_type_name")
    val incidentTypeName: String?,

    @SerializedName("status_name")
    val statusName: String?,

    @SerializedName("is_own")
    val isOwn: Boolean,

    @SerializedName("occurred_at")
    val occurredAt: String?,

    @SerializedName("reported_at")
    val reportedAt: String,

    @SerializedName("address")
    val address: String?
)

data class UserLocationDto(
    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double
)

data class MapDataDto(
    @SerializedName("my_incidents")
    val myIncidents: List<MapIncidentDto>,

    @SerializedName("other_incidents")
    val otherIncidents: List<MapIncidentDto>,

    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("radius_km")
    val radiusKm: Double,

    @SerializedName("user_location")
    val userLocation: UserLocationDto
)