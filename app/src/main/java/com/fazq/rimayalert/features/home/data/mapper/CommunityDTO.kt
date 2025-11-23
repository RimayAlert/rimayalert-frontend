package com.fazq.rimayalert.features.home.data.mapper

import com.google.gson.annotations.SerializedName

data class CommunityDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("radius")
    val radius: Double
)

data class CommunityValidationResponseDTO(
    @SerializedName("has_community")
    val hasCommunity: Boolean,
    @SerializedName("community")
    val community: CommunityDTO? = null,
    @SerializedName("message")
    val message: String? = null
)

data class AssignCommunityRequestDTO(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)