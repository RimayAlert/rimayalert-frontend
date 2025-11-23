package com.fazq.rimayalert.features.maps.domain.model

import com.fazq.rimayalert.features.maps.data.mapper.MapDataDto
import com.fazq.rimayalert.features.maps.data.mapper.MapIncidentDto
import com.fazq.rimayalert.features.maps.data.mapper.UserLocationDto

data class MapIncidentModel(
    val id: Int,
    val title: String,
    val description: String,
    val latitude: Double?,
    val longitude: Double?,
    val severityLevel: Int?,
    val incidentTypeName: String?,
    val statusName: String?,
    val isOwn: Boolean,
    val occurredAt: String?,
    val reportedAt: String,
    val address: String?
)

data class UserLocationModel(
    val latitude: Double,
    val longitude: Double
)

data class MapDataModel(
    val myIncidents: List<MapIncidentModel>,
    val otherIncidents: List<MapIncidentModel>,
    val totalCount: Int,
    val radiusKm: Double,
    val userLocation: UserLocationModel
)

fun MapIncidentDto.toDomain() = MapIncidentModel(
    id = id,
    title = title,
    description = description,
    latitude = latitude,
    longitude = longitude,
    severityLevel = severityLevel,
    incidentTypeName = incidentTypeName,
    statusName = statusName,
    isOwn = isOwn,
    occurredAt = occurredAt,
    reportedAt = reportedAt,
    address = address
)

fun UserLocationDto.toDomain() = UserLocationModel(
    latitude = latitude,
    longitude = longitude
)

fun MapDataDto.toDomain() = MapDataModel(
    myIncidents = myIncidents.map { it.toDomain() },
    otherIncidents = otherIncidents.map { it.toDomain() },
    totalCount = totalCount,
    radiusKm = radiusKm,
    userLocation = userLocation.toDomain()
)