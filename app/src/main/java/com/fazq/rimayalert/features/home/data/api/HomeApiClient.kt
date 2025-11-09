package com.fazq.rimayalert.features.home.data.api

import com.fazq.rimayalert.core.connection.response.ApiResponse
import com.fazq.rimayalert.features.home.data.api.HomeApiUrls.GET_INCIDENT_INFO
import com.fazq.rimayalert.features.home.data.mapper.IncidentDTO
import retrofit2.http.GET

interface HomeApiClient {

    @GET(GET_INCIDENT_INFO)
    suspend fun getIncidentInfo(): ApiResponse<List<IncidentDTO>>
}