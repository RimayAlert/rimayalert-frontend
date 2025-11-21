package com.fazq.rimayalert.core.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.permissionsDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_permissions")

class PermissionsManager(private val context: Context) {

    companion object {
        private val CAMERA_PERMISSION_GRANTED = booleanPreferencesKey("camera_permission_granted")
        private val CAMERA_PERMISSION_DENIED_PERMANENTLY =
            booleanPreferencesKey("camera_denied_permanently")
        private val STORAGE_PERMISSION_GRANTED = booleanPreferencesKey("storage_permission_granted")
        private val STORAGE_PERMISSION_DENIED_PERMANENTLY =
            booleanPreferencesKey("storage_denied_permanently")
        private val NOTIFICATION_PERMISSION_GRANTED =
            booleanPreferencesKey("notification_permission_granted")

        private val NOTIFICATION_PERMISSION_DENIED_PERMANENTLY =
            booleanPreferencesKey("notification_permission_denied_permanently")

    }

    suspend fun setCameraPermissionGranted(granted: Boolean) {
        context.permissionsDataStore.edit { prefs ->
            prefs[CAMERA_PERMISSION_GRANTED] = granted
            if (granted) {
                prefs[CAMERA_PERMISSION_DENIED_PERMANENTLY] = false
            }
        }
    }

    suspend fun setCameraPermissionDeniedPermanently(denied: Boolean) {
        context.permissionsDataStore.edit { prefs ->
            prefs[CAMERA_PERMISSION_DENIED_PERMANENTLY] = denied
        }
    }

    val isCameraPermissionGranted: Flow<Boolean> = context.permissionsDataStore.data.map { prefs ->
        prefs[CAMERA_PERMISSION_GRANTED] ?: false
    }

    val isCameraPermissionDeniedPermanently: Flow<Boolean> =
        context.permissionsDataStore.data.map { prefs ->
            prefs[CAMERA_PERMISSION_DENIED_PERMANENTLY] ?: false
        }

    suspend fun setStoragePermissionGranted(granted: Boolean) {
        context.permissionsDataStore.edit { prefs ->
            prefs[STORAGE_PERMISSION_GRANTED] = granted
            if (granted) {
                prefs[STORAGE_PERMISSION_DENIED_PERMANENTLY] = false
            }
        }
    }

    suspend fun setStoragePermissionDeniedPermanently(denied: Boolean) {
        context.permissionsDataStore.edit { prefs ->
            prefs[STORAGE_PERMISSION_DENIED_PERMANENTLY] = denied
        }
    }


    suspend fun setNotificationPermissionGranted(granted: Boolean) {
        context.permissionsDataStore.edit { prefs ->
            prefs[NOTIFICATION_PERMISSION_GRANTED] = granted
            if (granted) {
                prefs[NOTIFICATION_PERMISSION_DENIED_PERMANENTLY] = false
            }
        }
    }

    suspend fun setNotificationPermissionDeniedPermanently(denied: Boolean) {
        context.permissionsDataStore.edit { prefs ->
            prefs[NOTIFICATION_PERMISSION_DENIED_PERMANENTLY] = denied
        }
    }

    val isNotificationPermissionGranted: Flow<Boolean> =
        context.permissionsDataStore.data.map { prefs ->
            prefs[NOTIFICATION_PERMISSION_GRANTED] ?: false
        }

    val isNotificationDeniedPermanently: Flow<Boolean> =
        context.permissionsDataStore.data.map { prefs ->
            prefs[NOTIFICATION_PERMISSION_DENIED_PERMANENTLY] ?: false
        }

    val isStoragePermissionGranted: Flow<Boolean> = context.permissionsDataStore.data.map { prefs ->
        prefs[STORAGE_PERMISSION_GRANTED] ?: false
    }

    val isStoragePermissionDeniedPermanently: Flow<Boolean> =
        context.permissionsDataStore.data.map { prefs ->
            prefs[STORAGE_PERMISSION_DENIED_PERMANENTLY] ?: false
        }

    suspend fun clearAllPermissions() {
        context.permissionsDataStore.edit { it.clear() }
    }
}