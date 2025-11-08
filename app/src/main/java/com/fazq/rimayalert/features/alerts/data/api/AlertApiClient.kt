package com.fazq.rimayalert.features.alerts.data.api

import com.fazq.rimayalert.core.connection.response.ApiResponse
import com.fazq.rimayalert.features.alerts.data.api.AlertApiUrls.CREATE_ALERT
import com.fazq.rimayalert.features.alerts.data.mapper.AlertResponseDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AlertApiClient {

    @Multipart
    @POST(CREATE_ALERT)
    suspend fun createAlert(
        @Part("data") data: RequestBody,
        @Part image: MultipartBody.Part?
    ): ApiResponse<AlertResponseDTO>
}