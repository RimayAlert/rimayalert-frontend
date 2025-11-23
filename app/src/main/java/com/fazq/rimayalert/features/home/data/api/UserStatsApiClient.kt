package com.fazq.rimayalert.features.home.data.api

import com.fazq.rimayalert.core.connection.response.ApiResponse
import com.fazq.rimayalert.features.home.data.api.HomeApiUrls.GET_USER_STATS
import com.fazq.rimayalert.features.home.domain.model.UserStatsDTO
import retrofit2.http.GET

interface UserStatsApiClient {

    @GET(GET_USER_STATS)
    suspend fun getUserStats(): ApiResponse<UserStatsDTO>
}