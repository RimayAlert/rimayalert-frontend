package com.fazq.rimayalert.core.states

sealed class BaseUiState {
    data object LoadingState : BaseUiState()

    data class SuccessState<T>(val data: T) : BaseUiState()

    data class ErrorState(val message: String, val code: Int = 0) : BaseUiState()

    data object EmptyState : BaseUiState()
}

data class Event<out T>(val content: T, private var hasBeenHandled: Boolean = false) {

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}
