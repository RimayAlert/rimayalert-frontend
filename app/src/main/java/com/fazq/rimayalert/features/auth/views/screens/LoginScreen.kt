package com.fazq.rimayalert.features.auth.views.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.fazq.rimayalert.core.ui.components.ModernLoaderComponent
import com.fazq.rimayalert.core.ui.components.dialogs.ErrorDialogComponent
import com.fazq.rimayalert.core.ui.components.dialogs.SuccessDialogComponent
import com.fazq.rimayalert.features.auth.views.components.dialog.PermissionDialogComponent
import com.fazq.rimayalert.features.auth.views.components.sections.LoginContentComponent
import com.fazq.rimayalert.features.auth.views.event.LoginEvent
import com.fazq.rimayalert.features.auth.views.utils.fetchLocation
import com.fazq.rimayalert.features.auth.views.utils.findActivity
import com.fazq.rimayalert.features.auth.views.utils.hasLocationPermission
import com.fazq.rimayalert.features.auth.views.utils.isPermissionPermanentlyDenied
import com.fazq.rimayalert.features.auth.views.viewmodel.AuthViewModel
import com.google.android.gms.location.LocationServices

@SuppressLint("MissingPermission")
@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val uiState by authViewModel.uiState.collectAsState()

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var permissionDialogVisible by remember { mutableStateOf(false) }

    val openSettings = {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        )
        context.startActivity(intent)
    }

    val locationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val granted = result[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                result[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (granted) {
            permissionDialogVisible = false
            authViewModel.onEvent(LoginEvent.PermissionGranted)
            fetchLocation(fusedLocationClient, authViewModel)
        } else {
            authViewModel.onEvent(LoginEvent.PermissionDenied)
            permissionDialogVisible = true
        }
    }

    LaunchedEffect(Unit) {
        val hasPermission = hasLocationPermission(context)
        if (hasPermission) {
            authViewModel.onEvent(LoginEvent.PermissionGranted)
            fetchLocation(fusedLocationClient, authViewModel)
        } else if (!uiState.hasAskedPermissionBefore) {
            authViewModel.onEvent(LoginEvent.PermissionRequestAttempt)
            locationLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            onLoginSuccess()
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                val granted = hasLocationPermission(context)
                if (granted) {
                    permissionDialogVisible = false
                    authViewModel.onEvent(LoginEvent.PermissionGranted)
                    fetchLocation(fusedLocationClient, authViewModel)
                } else if (uiState.hasAskedPermissionBefore) {
                    permissionDialogVisible = true
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LoginContentComponent(
            uiState = uiState,
            onUserNameChange = { authViewModel.onEvent(LoginEvent.UsernameChanged(it)) },
            onPasswordChange = { authViewModel.onEvent(LoginEvent.PasswordChanged(it)) },
            onLoginClick = {
                if (!uiState.hasLocationPermission) {
                    permissionDialogVisible = true
                } else {
                    authViewModel.onEvent(LoginEvent.LoginButtonClicked)
                }
            },
            onRegisterClick = {
                if (!uiState.hasLocationPermission) {
                    permissionDialogVisible = true
                } else {
                    onRegisterClick()
                }
            },
            onForgotPasswordClick = onForgotPasswordClick,
            onPermissionRequired = {
                if (!uiState.hasLocationPermission) permissionDialogVisible = true
            }
        )

        ModernLoaderComponent(
            isLoading = uiState.isLoading,
            message = "Iniciando sesión...",
            fullScreen = false
        )
    }

    if (permissionDialogVisible && !uiState.hasLocationPermission) {
        val permanentlyDenied = activity?.let { isPermissionPermanentlyDenied(it) } ?: false

        PermissionDialogComponent(
            openDialog = true,
            onDismiss = { permissionDialogVisible = false },
            permanentlyDenied = permanentlyDenied,
            onRetry = {
                if (!permanentlyDenied) {
                    permissionDialogVisible = false
                    locationLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            },
            onOpenSettings = {
                permissionDialogVisible = false
                openSettings()
            }
        )
    }

    uiState.errorMessage?.let {
        ErrorDialogComponent(
            openDialog = true,
            message = it,
            onDismiss = {
                authViewModel.onEvent(LoginEvent.ClearErrorMessage)
            }
        )
    }

//    uiState.successMessage?.let {
//        SuccessDialogComponent(
//            openDialog = true,
//            message = it,
//            onDismiss = {
//                authViewModel.onEvent(LoginEvent.ClearSuccessMessage)
//            }
//        )
//    }
}