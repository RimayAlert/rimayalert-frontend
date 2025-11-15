package com.fazq.rimayalert.core.states

sealed class DialogState {
    data object None : DialogState()

    data class Error(
        val title: String,
        val message: String
    ) : DialogState()

    data class Success(
        val title: String,
        val message: String
    ) : DialogState()

    data class Confirmation(
        val title: String,
        val message: String,
        val onConfirm: () -> Unit
    ) : DialogState()
}