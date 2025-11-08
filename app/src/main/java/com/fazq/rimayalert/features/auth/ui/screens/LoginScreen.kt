package com.fazq.rimayalert.features.auth.ui.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fazq.rimayalert.core.preferences.LocationPermissionsManager
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.features.auth.domain.model.AuthModel
import com.fazq.rimayalert.features.auth.ui.components.LoginContentComponent
import com.fazq.rimayalert.features.auth.ui.state.LoginUiState
import com.fazq.rimayalert.features.auth.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val authUiState by authViewModel.authUiState.collectAsState()
    var localUiState by remember { mutableStateOf(LoginUiState()) }
    val snackbarHostState = remember { SnackbarHostState() }

    val locationPermissionsManager = remember { LocationPermissionsManager(context) }
    val wasLocationRequested by locationPermissionsManager.wasLocationPermissionRequested
        .collectAsStateWithLifecycle(initialValue = false)

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        scope.launch {
            val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

            val shouldShowRationale = !fineGranted && !coarseGranted

            locationPermissionsManager.handleLocationPermissionsResult(
                permissions = permissions,
                shouldShowRationale = shouldShowRationale
            )

            if (fineGranted || coarseGranted) {
                snackbarHostState.showSnackbar(
                    message = "Permisos de ubicación concedidos",
                    duration = SnackbarDuration.Short
                )
            } else {
                snackbarHostState.showSnackbar(
                    message = "Permisos de ubicación denegados. Algunas funciones pueden no estar disponibles.",
                    duration = SnackbarDuration.Long
                )
            }
        }
    }

    LaunchedEffect(authUiState) {
        when (val state = authUiState) {
            is BaseUiState.SuccessState<*> -> {
                val message = (state.data as? String) ?: "¡Inicio de sesión exitoso!"
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Short
                )
                locationPermissionsManager.syncPermissionsWithSystem()
                if (!wasLocationRequested && !locationPermissionsManager.hasAnyLocationPermission()) {
                    val permissionsToRequest =
                        locationPermissionsManager.getRequiredLocationPermissions()
                    locationPermissionLauncher.launch(permissionsToRequest)
                } else if (locationPermissionsManager.hasAnyLocationPermission()) {
                    onLoginSuccess()
                }
                authViewModel.resetState()
            }

            is BaseUiState.ErrorState -> {
                snackbarHostState.showSnackbar(
                    message = state.message,
                    duration = SnackbarDuration.Long
                )
                authViewModel.resetState()
            }

            else -> {}
        }
    }

    LaunchedEffect(wasLocationRequested, authUiState) {
        if (wasLocationRequested && authUiState is BaseUiState.SuccessState<*>) onLoginSuccess()
    }

    LoginContentComponent(
        uiState = localUiState,
        authState = authUiState,
        snackbarHostState = snackbarHostState,
        onUserNameChange = { localUiState = localUiState.copy(userName = it) },
        onPasswordChange = { localUiState = localUiState.copy(password = it) },
        onRememberMeChange = { localUiState = localUiState.copy(rememberMe = it) },
        onLoginClick = {
            authViewModel.auth(
                AuthModel(
                    username = "dev-test",
                    password = "devtest"
//                    username = localUiState.userName,
//                    password = localUiState.password
                )
            )
        },
        onRegisterClick = onRegisterClick,
        onForgotPasswordClick = onForgotPasswordClick
    )
}

