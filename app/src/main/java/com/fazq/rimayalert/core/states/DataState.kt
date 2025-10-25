package com.fazq.rimayalert.core.states

sealed class DataState<T> {
    data class Success<T>(val data: T, val code: Int) : DataState<T>()
    data class Error<T>(val message: String, val code: Int) : DataState<T>()

    companion object {
        fun <T> success(data: T, code: Int = 200) = Success(data, code)
        fun <T> error(message: String, code: Int = 0) = Error<T>(message, code)
    }
}

