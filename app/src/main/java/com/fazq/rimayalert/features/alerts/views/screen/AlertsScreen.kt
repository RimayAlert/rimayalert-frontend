package com.fazq.rimayalert.features.alerts.views.screen

import LocationEditDialog
import android.Manifest
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fazq.rimayalert.core.states.DialogState
import com.fazq.rimayalert.core.ui.components.ModernLoaderComponent
import com.fazq.rimayalert.core.ui.components.scaffold.AppBottomNavigationComponent
import com.fazq.rimayalert.core.ui.components.scaffold.AppScaffoldComponent
import com.fazq.rimayalert.core.ui.components.topBar.HomeTopBarComponent
import com.fazq.rimayalert.core.utils.ImagePickerManager
import com.fazq.rimayalert.features.alerts.views.component.sections.AlertsContentComponent
import com.fazq.rimayalert.features.alerts.views.event.AlertEvent
import com.fazq.rimayalert.features.alerts.views.viewmodel.AlertViewModel
import com.fazq.rimayalert.features.home.views.viewmodel.HomeViewModel
import kotlinx.coroutines.launch


@Composable
fun AlertsScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToAlerts: () -> Unit = {},
    onNavigateToMap: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    homeViewModel: HomeViewModel = hiltViewModel(),
    alertViewModel: AlertViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val permissionsManager = homeViewModel.permissionsManager
    val uiState by alertViewModel.uiState.collectAsStateWithLifecycle()

    val imagePickerManager = remember {
        ImagePickerManager(
            context = context,
            permissionsManager = permissionsManager,
            scope = scope,
            onImageSelected = { uri ->
                alertViewModel.onEvent(AlertEvent.ImageSelected(uri))
            },
            onError = { message ->
                scope.launch {
                    snackbarHostState.showSnackbar(message)
                }
            }
        )
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imagePickerManager.handleGalleryResult(uri)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        imagePickerManager.handleCameraResult(success)
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        val activity = context as? ComponentActivity
        val shouldShowRationale = activity?.shouldShowRequestPermissionRationale(
            Manifest.permission.CAMERA
        ) ?: false

        imagePickerManager.handleCameraPermissionResult(
            isGranted = isGranted,
            shouldShowRationale = shouldShowRationale,
            cameraLauncher = cameraLauncher
        )
    }

    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        val activity = context as? ComponentActivity
        val shouldShowRationale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            false
        } else {
            activity?.shouldShowRequestPermissionRationale(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) ?: false
        }

        imagePickerManager.handleStoragePermissionResult(
            isGranted = isGranted,
            shouldShowRationale = shouldShowRationale,
            galleryLauncher = galleryLauncher
        )
    }

    if (uiState.showLocationEditDialog) {
        LocationEditDialog(
            currentAddress = uiState.location,
            currentLatitude = uiState.latitude,
            currentLongitude = uiState.longitude,
            onDismiss = {
                alertViewModel.onEvent(AlertEvent.DismissLocationDialog)
            },
            onConfirm = { address, lat, lon ->
                alertViewModel.onEvent(
                    AlertEvent.ConfirmLocationEdit(
                        address = address,
                        latitude = lat,
                        longitude = lon
                    )
                )
            }
        )
    }

    LaunchedEffect(uiState.dialogState) {
        if (uiState.dialogState is DialogState.Success) {
            kotlinx.coroutines.delay(1500)
            alertViewModel.resetForm()
            onNavigateToHome()
        }
    }

    AppScaffoldComponent(
        topBar = {
            HomeTopBarComponent(uiState.userName, onNotificationClick)
        },
        bottomBar = {
            AppBottomNavigationComponent(
                currentRoute = 1,
                onHomeClick = onNavigateToHome,
                onAlertsClick = onNavigateToAlerts,
                onMapClick = onNavigateToMap,
                onProfileClick = onNavigateToProfile
            )
        },
        snackbarHostState = snackbarHostState
    ) { paddingValues ->
        AlertsContentComponent(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState,
            onEvent = alertViewModel::onEvent,
            onUploadImage = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    galleryLauncher.launch("image/*")
                } else {
                    storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            },
            onOpenCamera = {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        )
    }


    ModernLoaderComponent(
        isLoading = uiState.isLoading,
        message = "Enviando alerta...",
        fullScreen = false
    )
}