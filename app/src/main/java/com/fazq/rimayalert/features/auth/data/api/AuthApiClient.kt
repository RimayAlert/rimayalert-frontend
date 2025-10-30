package com.fazq.rimayalert.features.auth.data.api

import com.fazq.rimayalert.core.connection.response.ApiResponse
import com.fazq.rimayalert.features.auth.data.api.AuthApiUrls.AUTH_URL
import com.fazq.rimayalert.features.auth.data.mapper.AuthRequestDTO
import com.fazq.rimayalert.features.auth.data.mapper.AuthResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiClient {

    @POST(AUTH_URL)
    suspend fun authentication(
        @Body data: AuthRequestDTO
    ): ApiResponse<AuthResponseDTO>

}