package com.fazq.rimayalert.features.home.data.mapper

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommunityDTO(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("radius")
    val radius: Double
)

@Serializable
data class CommunityValidationResponseDTO(
    @SerialName("")
    val hasCommunity: Boolean,
    @SerialName("community")
    val community: CommunityDTO? = null,
    @SerialName("message")
    val message: String? = null
)

@Serializable
data class AssignCommunityRequestDTO(
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double
)