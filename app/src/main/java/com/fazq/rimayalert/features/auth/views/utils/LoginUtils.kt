package com.fazq.rimayalert.features.auth.views.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fazq.rimayalert.features.auth.views.event.LoginEvent
import com.fazq.rimayalert.features.auth.views.viewmodel.AuthViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority

fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}

@SuppressLint("MissingPermission")
fun fetchLocation(
    fusedLocationClient: FusedLocationProviderClient,
    viewModel: AuthViewModel
) {
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                viewModel.onEvent(
                    LoginEvent.SaveLocation(location.latitude, location.longitude)
                )
            } else {
                requestNewLocationData(fusedLocationClient, viewModel)
            }
        }
        .addOnFailureListener {
            requestNewLocationData(fusedLocationClient, viewModel)
        }
}

@SuppressLint("MissingPermission")
fun requestNewLocationData(
    fusedLocationClient: FusedLocationProviderClient,
    viewModel: AuthViewModel
) {
    val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        10000L // intervalo
    ).apply {
        setMinUpdateIntervalMillis(5000L)
        setMaxUpdates(1)
    }.build()

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation?.let { location ->
                viewModel.onEvent(
                    LoginEvent.SaveLocation(location.latitude, location.longitude)
                )
            }
        }
    }

    fusedLocationClient.requestLocationUpdates(
        locationRequest,
        locationCallback,
        Looper.getMainLooper()
    )
}

fun isPermissionPermanentlyDenied(activity: Activity): Boolean {
    val fineDenied = ActivityCompat.shouldShowRequestPermissionRationale(
        activity,
        Manifest.permission.ACCESS_FINE_LOCATION
    ).not()

    val coarseDenied = ActivityCompat.shouldShowRequestPermissionRationale(
        activity,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ).not()

    val fineGranted = ActivityCompat.checkSelfPermission(
        activity,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val coarseGranted = ActivityCompat.checkSelfPermission(
        activity,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    return (!fineGranted && fineDenied) || (!coarseGranted && coarseDenied)
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is android.content.ContextWrapper -> baseContext.findActivity()
    else -> null
}


