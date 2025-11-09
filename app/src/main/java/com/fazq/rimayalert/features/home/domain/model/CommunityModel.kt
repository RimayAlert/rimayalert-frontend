package com.fazq.rimayalert.features.home.domain.model

import com.fazq.rimayalert.features.home.data.mapper.AssignCommunityRequestDTO

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