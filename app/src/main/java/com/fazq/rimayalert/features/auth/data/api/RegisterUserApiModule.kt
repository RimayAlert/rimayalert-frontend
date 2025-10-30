package com.fazq.rimayalert.features.auth.data.api

import com.fazq.rimayalert.core.connection.response.ApiResponse
import com.fazq.rimayalert.features.auth.data.api.AuthApiUrls.REGISTER_USER
import com.fazq.rimayalert.features.auth.domain.model.RegisterUserModel
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterUserApiClient {

    @POST(REGISTER_USER)
    suspend fun registerUser(
        @Body request: RegisterUserModel
    ): ApiResponse<JsonObject>

}