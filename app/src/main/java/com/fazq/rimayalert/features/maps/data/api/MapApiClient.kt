package com.fazq.rimayalert.features.maps.data.api

import com.fazq.rimayalert.core.connection.response.ApiResponse
import com.fazq.rimayalert.features.maps.data.api.MapApiUrls.DETAIL_MAP
import com.fazq.rimayalert.features.maps.data.mapper.MapDataDto
import retrofit2.http.GET

interface MapApiClient {

    @GET(DETAIL_MAP)
    suspend fun getMapIncidents(): ApiResponse<MapDataDto>
}