package com.fazq.rimayalert.core.connection.location

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import com.fazq.rimayalert.core.preferences.LocationPermissionsManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val address: String
)

@Singleton
class LocationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val locationPermissionsManager: LocationPermissionsManager
) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val geocoder: Geocoder = Geocoder(context, Locale.getDefault())

    fun hasLocationPermission(): Boolean {
        return locationPermissionsManager.hasAnyLocationPermission()
    }

    suspend fun getCurrentLocation(): Result<LocationData> {
        if (!hasLocationPermission()) {
            return Result.failure(
                SecurityException(locationPermissionsManager.getPermissionDeniedMessage())
            )
        }

        return suspendCancellableCoroutine { continuation ->
            try {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            val address =
                                getAddressFromLocation(location.latitude, location.longitude)
                            continuation.resume(
                                Result.success(
                                    LocationData(
                                        latitude = location.latitude,
                                        longitude = location.longitude,
                                        address = address
                                    )
                                )
                            )
                        } else {
                            requestNewLocation { newLocation ->
                                if (newLocation != null) {
                                    val address = getAddressFromLocation(
                                        newLocation.latitude,
                                        newLocation.longitude
                                    )
                                    continuation.resume(
                                        Result.success(
                                            LocationData(
                                                latitude = newLocation.latitude,
                                                longitude = newLocation.longitude,
                                                address = address
                                            )
                                        )
                                    )
                                } else {
                                    continuation.resume(
                                        Result.failure(Exception("No se pudo obtener la ubicación"))
                                    )
                                }
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        continuation.resume(Result.failure(exception))
                    }
            } catch (e: SecurityException) {
                continuation.resume(Result.failure(e))
            }
        }
    }

    fun getLocationUpdates(intervalMillis: Long = 10000): Flow<LocationData> = callbackFlow {
        if (!hasLocationPermission()) {
            close(SecurityException(locationPermissionsManager.getPermissionDeniedMessage()))
            return@callbackFlow
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            intervalMillis
        ).apply {
            setMinUpdateIntervalMillis(intervalMillis / 2)
            setWaitForAccurateLocation(false)
        }.build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    val address = getAddressFromLocation(location.latitude, location.longitude)
                    trySend(
                        LocationData(
                            latitude = location.latitude,
                            longitude = location.longitude,
                            address = address
                        )
                    )
                }
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            close(e)
        }

        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    private fun requestNewLocation(callback: (Location?) -> Unit) {
        if (!hasLocationPermission()) {
            callback(null)
            return
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            0
        ).build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                callback(result.lastLocation)
                fusedLocationClient.removeLocationUpdates(this)
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            callback(null)
        }
    }

    private fun getAddressFromLocation(latitude: Double, longitude: Double): String {
        return try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                buildString {
                    address.thoroughfare?.let { append("$it, ") }
                    address.locality?.let { append("$it, ") }
                    address.adminArea?.let { append(it) }
                }.ifEmpty { "Ubicación desconocida" }
            } else {
                "Ubicación desconocida"
            }
        } catch (e: Exception) {
            "Lat: ${String.format("%.4f", latitude)}, Lon: ${String.format("%.4f", longitude)}"
        }
    }

    suspend fun getAddressFromCoordinates(latitude: Double, longitude: Double): String {
        return getAddressFromLocation(latitude, longitude)
    }

}