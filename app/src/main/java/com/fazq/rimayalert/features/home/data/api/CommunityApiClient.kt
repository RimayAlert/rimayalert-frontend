package com.fazq.rimayalert.features.home.data.api

import com.fazq.rimayalert.core.connection.response.ApiResponse
import com.fazq.rimayalert.features.home.data.api.HomeApiUrls.ASSIGN_COMMUNITY
import com.fazq.rimayalert.features.home.data.mapper.AssignCommunityRequestDTO
import com.fazq.rimayalert.features.home.data.mapper.CommunityValidationResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface CommunityApiClient {

    @POST(ASSIGN_COMMUNITY)
    suspend fun validateOrAssignCommunity(
        @Body data: AssignCommunityRequestDTO
    ): ApiResponse<CommunityValidationResponseDTO>

}