package com.fazq.rimayalert.core.connection.singleton

import com.fazq.rimayalert.features.auth.data.api.AuthApiClient
import com.fazq.rimayalert.features.auth.data.api.RegisterUserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideRegisterUserApiClient(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
    ): RegisterUserApiClient = retrofitBuilder
        .client(okHttpClient)
        .build()
        .create(RegisterUserApiClient::class.java)

    @Singleton
    @Provides
    fun provideAuthApiClient(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
    ): AuthApiClient = retrofitBuilder
        .client(okHttpClient)
        .build()
        .create(AuthApiClient::class.java)

}