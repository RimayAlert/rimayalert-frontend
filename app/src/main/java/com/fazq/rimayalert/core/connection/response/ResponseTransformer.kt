package com.fazq.rimayalert.core.connection.response

@SuspensionFunction
suspend fun <T> ApiResponse<T>.onSuccessSuspend(
    onResult: suspend ApiResponse.ApiSuccessResponse<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.ApiSuccessResponse)
        onResult(this)
    return this
}

@SuspensionFunction
suspend fun <T> ApiResponse<T>.onErrorSuspend(
    onResult: suspend ApiResponse.ApiFailureResponse.Error<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.ApiFailureResponse.Error)
        onResult(this)
    return this
}

@SuspensionFunction
suspend fun <T> ApiResponse<T>.onExceptionSuspend(
    onResult: suspend ApiResponse.ApiFailureResponse.Exception<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.ApiFailureResponse.Exception)
        onResult(this)
    return this
}

fun <T> ApiResponse.ApiFailureResponse.Error<T>.message(): String = toString()

fun <T> ApiResponse.ApiFailureResponse.Exception<T>.message(): String = toString()