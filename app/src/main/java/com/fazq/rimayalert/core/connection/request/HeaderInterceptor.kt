package com.fazq.rimayalert.core.connection.request

import com.fazq.rimayalert.BuildConfig
import com.fazq.rimayalert.core.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeaderInterceptor @Inject constructor() : Interceptor {
    @Inject
    lateinit var tokenManager: TokenManager
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenManager.getToken()
        val request = chain.request()
        val requestBuilder = request.newBuilder()
            .addHeader("app-source", "fazq-Rimay-alert-app")
        if (token.isNotEmpty()) {
            requestBuilder.addHeader("Authorization", token)
        }
        requestBuilder.addHeader("content-type", "application/json")
        requestBuilder.addHeader("app-version", BuildConfig.VERSION_NAME)
        return chain.proceed(requestBuilder.build())
    }
}