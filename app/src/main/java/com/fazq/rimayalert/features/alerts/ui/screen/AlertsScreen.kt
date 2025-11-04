package com.fazq.rimayalert.features.alerts.ui.screen

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.fazq.rimayalert.core.ui.components.scaffold.AppBottomNavigation
import com.fazq.rimayalert.core.ui.components.scaffold.AppScaffold
import com.fazq.rimayalert.core.ui.components.topBar.HomeTopBar
import com.fazq.rimayalert.core.ui.extensions.getDisplayName
import com.fazq.rimayalert.core.utils.ImageUtils
import com.fazq.rimayalert.features.alerts.ui.component.AlertsContentComponent
import com.fazq.rimayalert.features.alerts.ui.viewmodel.AlertViewModel
import com.fazq.rimayalert.features.home.ui.states.HomeUiState
import com.fazq.rimayalert.features.home.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import java.io.File

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

    val user by homeViewModel.user.collectAsStateWithLifecycle()
    val alertUiState by alertViewModel.alertUiState.collectAsStateWithLifecycle()
    val sendAlertState by alertViewModel.sendAlertState.collectAsStateWithLifecycle()
    var localUiState by remember { mutableStateOf(HomeUiState()) }
    var currentPhotoFile by remember { mutableStateOf<File?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            alertViewModel.updateImageUri(it.toString())
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && currentPhotoFile != null) {
            val uri = Uri.fromFile(currentPhotoFile)
            alertViewModel.updateImageUri(uri.toString())
        } else {
            ImageUtils.deleteImageFile(currentPhotoFile)
            currentPhotoFile = null
        }
    }


    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val photoFile = ImageUtils.createImageFile(context)
            photoFile?.let {
                currentPhotoFile = it
                val photoUri = ImageUtils.getImageUri(context, it)
                cameraLauncher.launch(photoUri)
            } ?: run {
                scope.launch {
                    snackbarHostState.showSnackbar("Error al crear archivo de imagen")
                }
            }
        } else {
            scope.launch {
                snackbarHostState.showSnackbar(
                    "Permiso de cámara denegado. Ve a configuración para habilitarlo."
                )
            }
        }
    }

    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            scope.launch {
                snackbarHostState.showSnackbar(
                    "Permiso de almacenamiento denegado. Ve a configuración para habilitarlo."
                )
            }
        }
    }

    LaunchedEffect(user) {
        user?.let { userData ->
            localUiState = localUiState.copy(userName = userData.getDisplayName())
        }
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


    AppScaffold(
        topBar = {
            HomeTopBar(localUiState.userName, onNotificationClick)
        },
        bottomBar = {
            AppBottomNavigation(
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
//        AppScaffold(
//            topBar = {
//                HomeTopBar(fakeUiState.userName, onNotificationClick = {})
//            },
//            bottomBar = {
//                AppBottomNavigation(
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
