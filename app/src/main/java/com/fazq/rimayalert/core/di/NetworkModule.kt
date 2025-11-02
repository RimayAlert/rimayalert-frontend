package com.fazq.rimayalert.core.di

import com.fazq.rimayalert.BuildConfig
import com.fazq.rimayalert.core.connection.request.HeaderInterceptor
import com.fazq.rimayalert.core.connection.response.ApiResponseCallAdapterFactory
import com.fazq.rimayalert.core.functions.isPrintable
import com.fazq.rimayalert.core.functions.removeInvalidCharacters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor {
            println(
                when {
                    it.isPrintable() -> it
                    else -> it.removeInvalidCharacters()
                }
            )
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor,
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(headerInterceptor)
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .build()
    }


}