package com.fazq.rimayalert.core.preferences

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.locationPermissionsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "location_permissions"
)

class LocationPermissionsManager(
    private val context: Context
) {
    companion object {
        private val FINE_LOCATION_GRANTED = booleanPreferencesKey("fine_location_granted")
        private val COARSE_LOCATION_GRANTED = booleanPreferencesKey("coarse_location_granted")
        private val LOCATION_DENIED_PERMANENTLY =
            booleanPreferencesKey("location_denied_permanently")
        private val LOCATION_PERMISSION_REQUESTED =
            booleanPreferencesKey("location_permission_requested")
    }

    val isFineLocationGranted: Flow<Boolean> =
        context.locationPermissionsDataStore.data.map { prefs ->
            prefs[FINE_LOCATION_GRANTED] ?: false
        }

    val isCoarseLocationGranted: Flow<Boolean> =
        context.locationPermissionsDataStore.data.map { prefs ->
            prefs[COARSE_LOCATION_GRANTED] ?: false
        }

    val isLocationDeniedPermanently: Flow<Boolean> =
        context.locationPermissionsDataStore.data.map { prefs ->
            prefs[LOCATION_DENIED_PERMANENTLY] ?: false
        }

    val wasLocationPermissionRequested: Flow<Boolean> =
        context.locationPermissionsDataStore.data.map { prefs ->
            prefs[LOCATION_PERMISSION_REQUESTED] ?: false
        }

    fun hasFineLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun hasCoarseLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun hasAnyLocationPermission(): Boolean {
        return hasFineLocationPermission() || hasCoarseLocationPermission()
    }

    suspend fun setFineLocationGranted(granted: Boolean) {
        context.locationPermissionsDataStore.edit { prefs ->
            prefs[FINE_LOCATION_GRANTED] = granted
            if (granted) {
                prefs[LOCATION_DENIED_PERMANENTLY] = false
            }
        }
    }

    suspend fun setCoarseLocationGranted(granted: Boolean) {
        context.locationPermissionsDataStore.edit { prefs ->
            prefs[COARSE_LOCATION_GRANTED] = granted
            if (granted) {
                prefs[LOCATION_DENIED_PERMANENTLY] = false
            }
        }
    }

    suspend fun setLocationDeniedPermanently(denied: Boolean) {
        context.locationPermissionsDataStore.edit { prefs ->
            prefs[LOCATION_DENIED_PERMANENTLY] = denied
        }
    }

    suspend fun markLocationPermissionAsRequested() {
        context.locationPermissionsDataStore.edit { prefs ->
            prefs[LOCATION_PERMISSION_REQUESTED] = true
        }
    }

    suspend fun syncPermissionsWithSystem() {
        context.locationPermissionsDataStore.edit { prefs ->
            prefs[FINE_LOCATION_GRANTED] = hasFineLocationPermission()
            prefs[COARSE_LOCATION_GRANTED] = hasCoarseLocationPermission()
            if (hasAnyLocationPermission()) {
                prefs[LOCATION_DENIED_PERMANENTLY] = false
            }
        }
    }

    suspend fun handleLocationPermissionsResult(
        permissions: Map<String, Boolean>,
        shouldShowRationale: Boolean
    ) {
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        setFineLocationGranted(fineGranted)
        setCoarseLocationGranted(coarseGranted)

        if (!fineGranted && !coarseGranted && !shouldShowRationale) {
            setLocationDeniedPermanently(true)
        }

        markLocationPermissionAsRequested()
    }

    suspend fun clearAllPermissions() {
        context.locationPermissionsDataStore.edit { it.clear() }
    }


    fun getRequiredLocationPermissions(): Array<String> {
        return arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    fun getPermissionDeniedMessage(): String {
        return if (!hasFineLocationPermission() && !hasCoarseLocationPermission()) {
            "Se necesitan permisos de ubicación para usar esta función. Por favor, habilítalos en Configuración."
        } else if (!hasFineLocationPermission()) {
            "Se necesita ubicación precisa para usar esta función. Por favor, habilítala en Configuración."
        } else {
            "Permisos de ubicación no disponibles."
        }
    }

}