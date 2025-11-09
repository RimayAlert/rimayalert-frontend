package com.fazq.rimayalert.features.home.data.api

import com.fazq.rimayalert.core.connection.response.ApiResponse
import com.fazq.rimayalert.features.home.data.api.HomeApiUrls.ASSIGN_COMMUNITY
import com.fazq.rimayalert.features.home.data.api.HomeApiUrls.CHECK_COMMUNITY_STATUS
import com.fazq.rimayalert.features.home.data.mapper.AssignCommunityRequestDTO
import com.fazq.rimayalert.features.home.data.mapper.CommunityValidationResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CommunityApiClient {

    @GET(CHECK_COMMUNITY_STATUS)
    suspend fun checkCommunityStatus(
    ): ApiResponse<CommunityValidationResponseDTO>

    @POST(ASSIGN_COMMUNITY)
    suspend fun assignCommunity(
        @Body data: AssignCommunityRequestDTO
    ): ApiResponse<CommunityValidationResponseDTO>

}