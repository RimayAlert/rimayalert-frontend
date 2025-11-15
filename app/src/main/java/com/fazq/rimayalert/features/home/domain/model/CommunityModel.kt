package com.fazq.rimayalert.features.home.domain.model

import com.fazq.rimayalert.features.home.data.mapper.CommunityDTO
import com.fazq.rimayalert.features.home.data.mapper.CommunityValidationResponseDTO

data class CommunityModel(
    val id: Int,
    val name: String,
    val description: String? = null,
    val latitude: Double,
    val longitude: Double,
    val radius: Double
)

data class AssignCommunityRequestModel(
    val latitude: Double,
    val longitude: Double
)

data class CommunityValidationResponseModel(
    val hasCommunity: Boolean,
    val community: CommunityModel?,
    val message: String? = null
)

fun CommunityValidationResponseDTO.toDomain() = CommunityValidationResponseModel(
    hasCommunity = hasCommunity,
    community = community?.toDomain(),
    message = message
)

fun CommunityDTO.toDomain() = CommunityModel(
    id = id,
    name = name,
    description = description,
    latitude = latitude,
    longitude = longitude,
    radius = radius
)
