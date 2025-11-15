package com.fazq.rimayalert.features.alerts.ui.screen

import android.Manifest
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.core.ui.components.scaffold.AppBottomNavigationComponent
import com.fazq.rimayalert.core.ui.components.scaffold.AppScaffoldComponent
import com.fazq.rimayalert.core.ui.components.topBar.HomeTopBarComponent
import com.fazq.rimayalert.core.ui.extensions.getDisplayName
import com.fazq.rimayalert.core.utils.ImagePickerManager
import com.fazq.rimayalert.features.alerts.ui.component.sections.AlertsContentComponent
import com.fazq.rimayalert.features.alerts.ui.viewmodel.AlertViewModel
import com.fazq.rimayalert.features.home.ui.states.HomeUiState
import com.fazq.rimayalert.features.home.ui.viewmodel.HomeViewModel
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
    val isCameraGranted by homeViewModel.isCameraGranted.collectAsState(initial = false)
    val isStorageGranted by homeViewModel.isStorageGranted.collectAsState(initial = false)

    val alertUiState by alertViewModel.alertUiState.collectAsStateWithLifecycle()
    val sendAlertState by alertViewModel.sendAlertState.collectAsStateWithLifecycle()
    var localUiState by remember { mutableStateOf(HomeUiState()) }

    val imagePickerManager = remember {
        ImagePickerManager(
            context = context,
            permissionsManager = permissionsManager,
            scope = scope,
            onImageSelected = { uri -> alertViewModel.updateImageUri(uri) },
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



    LaunchedEffect(sendAlertState) {
        when (sendAlertState) {
            is BaseUiState.SuccessState<*> -> {
                val message = (sendAlertState as BaseUiState.SuccessState<*>).data as? String
                    ?: "Alerta enviada exitosamente"
                snackbarHostState.showSnackbar(
                    message = message,
                    withDismissAction = true
                )
                alertViewModel.resetState()
                onNavigateToHome()
            }

            is BaseUiState.ErrorState -> {
                snackbarHostState.showSnackbar(
                    message = (sendAlertState as BaseUiState.ErrorState).message,
                    duration = SnackbarDuration.Long
                )
                alertViewModel.resetState()
            }

            is BaseUiState.LoadingState -> {}
            is BaseUiState.EmptyState -> {}
        }
    }


    AppScaffoldComponent(
        topBar = {
            HomeTopBarComponent(localUiState.userName, onNotificationClick)
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
            uiState = alertUiState,
            onTypeSelected = alertViewModel::onTypeSelected,
            onDescriptionChanged = alertViewModel::onDescriptionChanged,
            onUploadImage = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    galleryLauncher.launch("image/*")
                } else {
                    storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            },
            onSendAlert = alertViewModel::sendAlert,
            onOpenCamera = {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            },
            onLocationEdit = alertViewModel::onLocationEdit,
            onUseMap = alertViewModel::onUseMap,
            onRemoveImage = alertViewModel::removeImage
        )
    }
}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun AlertsScreenPreview() {
//    RimayAlertTheme {
//        val fakeUiState = HomeUiState(userName = "Dev")
//        AppScaffoldComponent(
//            topBar = {
//                HomeTopBarComponent(fakeUiState.userName, onNotificationClick = {})
//            },
//            bottomBar = {
//                AppBottomNavigationComponent(
//                    currentRoute = 1,
//                    onHomeClick = {},
//                    onAlertsClick = {},
//                    onMapClick = {},
//                    onProfileClick = {}
//                )
//            },
//            snackbarHostState = remember { SnackbarHostState() }
//        ) { paddingValues ->
//            AlertsContentComponent(uiState = fakeUiState)
//        }
//    }
//}
