package com.fazq.rimayalert.features.alerts.data.api

import com.fazq.rimayalert.core.connection.response.ApiResponse
import com.fazq.rimayalert.features.alerts.data.api.AlertApiUrls.CREATE_ALERT
import com.fazq.rimayalert.features.alerts.data.mapper.AlertRequestDTO
import com.fazq.rimayalert.features.alerts.data.mapper.AlertResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AlertApiClient {

    @POST(CREATE_ALERT)
    suspend fun createAlert(
        @Body data: AlertRequestDTO
    ): ApiResponse<AlertResponseDTO>
}