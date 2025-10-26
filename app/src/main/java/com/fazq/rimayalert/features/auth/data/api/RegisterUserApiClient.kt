package com.fazq.rimayalert.features.auth.data.api

import com.fazq.rimayalert.core.connection.response.ApiResponse
import com.fazq.rimayalert.features.auth.data.api.AuthApiUrls.REGISTER_USER
import com.fazq.rimayalert.features.auth.domain.model.RegisterUserModel
import com.google.gson.JsonObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegisterUserApiModule {
    @Singleton
    @Provides
    fun provideRegisterUserApiClient(
        retrofitBuilder: retrofit2.Retrofit.Builder,
        okHttpClient: okhttp3.OkHttpClient,
    ): RegisterUserApiClient = retrofitBuilder
        .client(okHttpClient)
        .build()
        .create(RegisterUserApiClient::class.java)
}

interface RegisterUserApiClient {

    @POST(REGISTER_USER)
    suspend fun registerUser(
        @Body request: RegisterUserModel
    ): ApiResponse<JsonObject>
}