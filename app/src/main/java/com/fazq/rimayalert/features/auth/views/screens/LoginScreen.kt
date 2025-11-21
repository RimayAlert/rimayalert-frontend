package com.fazq.rimayalert.features.auth.views.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.fazq.rimayalert.core.ui.components.dialogs.ErrorDialogComponent
import com.fazq.rimayalert.core.ui.components.dialogs.SuccessDialogComponent
import com.fazq.rimayalert.features.auth.views.components.sections.LoginContentComponent
import com.fazq.rimayalert.features.auth.views.event.LoginEvent
import com.fazq.rimayalert.features.auth.views.viewmodel.AuthViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

@SuppressLint("MissingPermission")
@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val loginUiState by authViewModel.uiState.collectAsState()

    val context = LocalContext.current

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var permissionsRequested by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (fineLocationGranted || coarseLocationGranted) {
            // Obtener y guardar ubicación
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    authViewModel.onEvent(
                        LoginEvent.SaveLocation(it.latitude, it.longitude)
                    )
                } ?: run {
                    // Si no hay última ubicación, solicitar una nueva
                    requestNewLocationData(fusedLocationClient, authViewModel)
                }
            }.addOnFailureListener {
                requestNewLocationData(fusedLocationClient, authViewModel)
            }
        } else {
            // Permisos denegados - mostrar diálogo
            showPermissionDialog = true
        }
    }

    LaunchedEffect(Unit) {
        if (!permissionsRequested) {
            permissionsRequested = true
            kotlinx.coroutines.delay(300) // Pequeño delay para que se cargue la UI
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
    LaunchedEffect(loginUiState.loginSuccess) {
        if (loginUiState.loginSuccess) {
            onLoginSuccess()
        }
    }


    LaunchedEffect(loginUiState.loginSuccess) {
        if (loginUiState.loginSuccess) {
            // Solicitar permisos de ubicación después del login exitoso
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Permisos Requeridos") },
            text = {
                Text(
                    "La aplicación necesita acceso a tu ubicación para funcionar correctamente. " +
                            "Sin este permiso no podrás registrarte ni usar las funcionalidades principales."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showPermissionDialog = false
                        locationPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                ) {
                    Text("Conceder Permisos")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        // Cerrar la app si no acepta permisos
                        (context as? Activity)?.finish()
                    }
                ) {
                    Text("Salir de la App")
                }
            }
        )
    }


    loginUiState.successMessage?.let { message ->
        SuccessDialogComponent(
            openDialog = true,
            message = message,
            onDismiss = {
                authViewModel.onEvent(LoginEvent.ClearSuccessMessage)
            }
        )
    }

    loginUiState.errorMessage?.let { message ->
        ErrorDialogComponent(
            openDialog = true,
            message = message,
            onDismiss = {
                authViewModel.onEvent(LoginEvent.ClearErrorMessage)
            }
        )
    }


    LoginContentComponent(
        uiState = loginUiState,
        onUserNameChange = {
            authViewModel.onEvent(LoginEvent.UsernameChanged(it.trim()))
        },
        onPasswordChange = {
            authViewModel.onEvent(LoginEvent.PasswordChanged(it.trim()))
        },
        onRememberMeChange = { },
        onLoginClick = {
            authViewModel.onEvent(LoginEvent.LoginButtonClicked)
        },
        onRegisterClick = onRegisterClick,
        onForgotPasswordClick = onForgotPasswordClick
    )
}


@SuppressLint("MissingPermission")
private fun requestNewLocationData(
    fusedLocationClient: FusedLocationProviderClient,
    viewModel: AuthViewModel
) {
    val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        10000
    ).apply {
        setMinUpdateIntervalMillis(5000)
        setMaxUpdates(1)
    }.build()

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { location ->
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
