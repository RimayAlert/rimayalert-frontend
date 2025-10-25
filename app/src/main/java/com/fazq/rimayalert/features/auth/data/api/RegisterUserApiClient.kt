package com.fazq.rimayalert.features.auth.data.api

import com.fazq.rimayalert.core.connection.response.ApiResponse
import com.fazq.rimayalert.features.auth.data.mapper.RegisterUserDTO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.http.GET
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FoodCategoryApiModule {
    @Singleton
    @Provides
    fun provideFoodCategoryApiClient(
        retrofitBuilder: retrofit2.Retrofit.Builder,
        okHttpClient: okhttp3.OkHttpClient,
    ): RegisterUserApiClient = retrofitBuilder
        .client(okHttpClient)
        .build()
        .create(RegisterUserApiClient::class.java)
}

interface RegisterUserApiClient {
    @GET("register")
    suspend fun registerUser(): ApiResponse<List<RegisterUserDTO>>
}