package com.fazq.rimayalert.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController

object SavedStateUtils {

    fun <T> save(navController: NavController, key: String, value: T): Boolean {
        return try {
            navController.currentBackStackEntry?.savedStateHandle?.set(key, value)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    @Composable
    fun <T> read(
        navController: NavController,
        key: String,
        autoRemove: Boolean = true,
        onValue: (T) -> Unit
    ) {
        val savedStateHandle: SavedStateHandle? =
            navController.previousBackStackEntry?.savedStateHandle

        LaunchedEffect(key, savedStateHandle) {
            savedStateHandle?.get<T>(key)?.let { value ->
                onValue(value)
                if (autoRemove) {
                    savedStateHandle.remove<T>(key)
                }
            }
        }
    }
}