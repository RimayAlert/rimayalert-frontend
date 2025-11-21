package com.fazq.rimayalert.core.services

import android.content.Context
import android.os.Build
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonObject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FCMManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val TAG = "FCMManager"
        private const val PREFS_NAME = "fcm_prefs"
        private const val KEY_FCM_TOKEN = "fcm_token"
    }

    /**
     * Obtiene el token FCM actual de forma asíncrona
     */
    suspend fun getToken(): String? {
        return try {
            val token = FirebaseMessaging.getInstance().token.await()
            Log.d(TAG, "Token obtenido: $token")
            saveToken(token)
            token
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener token FCM: ${e.message}", e)
            null
        }
    }

    /**
     * Obtiene el token con callback (estilo de tu código anterior)
     */
    fun getToken(callback: (String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Error obteniendo token FCM", task.exception)
                return@addOnCompleteListener
            }

            val token = task.result
            Log.d(TAG, "Token FCM: $token")
            saveToken(token)
            callback(token)
        }
    }

    /**
     * Obtiene el token guardado localmente
     */
    fun getSavedToken(): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_FCM_TOKEN, null)
    }

    /**
     * Guarda el token en SharedPreferences
     */
    private fun saveToken(token: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_FCM_TOKEN, token).apply()
        Log.d(TAG, "Token guardado localmente")
    }

    /**
     * Limpia el token (útil para logout)
     */
    fun clearToken() {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_FCM_TOKEN).apply()
        Log.d(TAG, "Token eliminado")
    }

    /**
     * Prepara el payload JSON para enviar al backend
     * (Similar a tu getPayloadPushTokenJson)
     */
    fun getPayloadPushTokenJson(token: String): JsonObject {
        val metadata = JsonObject().apply {
            addProperty("os_version", Build.VERSION.RELEASE)
            addProperty("brand", Build.BRAND)
            addProperty("model", Build.MODEL)
            addProperty("sdk_int", Build.VERSION.SDK_INT.toString())
        }

        return JsonObject().apply {
            addProperty("fcmToken", token)
            addProperty("deviceId", "${Build.BRAND}_${Build.MODEL}")
            add("metadata", metadata)
        }
    }

    /**
     * Obtiene el nombre del dispositivo
     */
    fun getDeviceName(): String {
        return "${Build.BRAND} ${Build.MODEL}"
    }
}