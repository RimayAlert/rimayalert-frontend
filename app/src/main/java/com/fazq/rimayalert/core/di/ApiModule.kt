package com.fazq.rimayalert.core.di

import com.fazq.rimayalert.features.alerts.data.api.AlertApiClient
import com.fazq.rimayalert.features.auth.data.api.AuthApiClient
import com.fazq.rimayalert.features.auth.data.api.RegisterUserApiClient
import com.fazq.rimayalert.features.home.data.api.CommunityApiClient
import com.fazq.rimayalert.features.home.data.api.HomeApiClient
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

    @Singleton
    @Provides
    fun providerAlertApiClient(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
    ): AlertApiClient = retrofitBuilder
        .client(okHttpClient)
        .build()
        .create(AlertApiClient::class.java)

    @Singleton
    @Provides
    fun provideCommunityApiClient(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
    ): CommunityApiClient = retrofitBuilder
        .client(okHttpClient)
        .build()
        .create(CommunityApiClient::class.java)

    @Singleton
    @Provides
    fun provideHomeApiClient(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
    ): HomeApiClient = retrofitBuilder
        .client(okHttpClient)
        .build()
        .create(HomeApiClient::class.java)


}