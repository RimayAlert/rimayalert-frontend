package com.fazq.rimayalert.core.connection.response

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                ApiResponse::class.java -> {
                    val responseType = getParameterUpperBound(0, callType as ParameterizedType)
                    ApiResponseCallAdapter(responseType)
                }
                else -> null
            }
        }
        else -> null
    }
}