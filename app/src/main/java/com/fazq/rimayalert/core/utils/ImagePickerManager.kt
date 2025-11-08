package com.fazq.rimayalert.core.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.fazq.rimayalert.core.preferences.PermissionsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date
import java.util.Locale

class ImagePickerManager(
    private val context: Context,
    private val permissionsManager: PermissionsManager,
    private val scope: CoroutineScope,
    private val onImageSelected: (Uri) -> Unit,
    private val onError: (String) -> Unit
) {
    private var currentPhotoUri: Uri? = null

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun hasStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            true
        } else {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun handleCameraPermissionRequest(
        permissionLauncher: ManagedActivityResultLauncher<String, Boolean>
    ) {
        scope.launch {
            val isDeniedPermanently = permissionsManager.isCameraPermissionDeniedPermanently.first()

            if (isDeniedPermanently) {
                onError("Permiso de cámara denegado permanentemente. Por favor, habilítalo en Configuración.")
                return@launch
            }

            if (hasCameraPermission()) {
                permissionsManager.setCameraPermissionGranted(true)
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    fun handleCameraPermissionResult(
        isGranted: Boolean,
        shouldShowRationale: Boolean,
        cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>
    ) {
        scope.launch {
            if (isGranted) {
                permissionsManager.setCameraPermissionGranted(true)
                permissionsManager.setCameraPermissionDeniedPermanently(false)
                launchCamera(cameraLauncher)
            } else {
                permissionsManager.setCameraPermissionGranted(false)

                if (!shouldShowRationale) {
                    permissionsManager.setCameraPermissionDeniedPermanently(true)
                    onError("Permiso de cámara denegado permanentemente. Por favor, habilítalo en Configuración.")
                } else {
                    onError("Permiso de cámara denegado")
                }
            }
        }
    }

    fun handleStoragePermissionRequest(
        permissionLauncher: ManagedActivityResultLauncher<String, Boolean>
    ) {
        scope.launch {
            val isDeniedPermanently =
                permissionsManager.isStoragePermissionDeniedPermanently.first()

            if (isDeniedPermanently) {
                onError("Permiso de almacenamiento denegado permanentemente. Por favor, habilítalo en Configuración.")
                return@launch
            }

            if (hasStoragePermission()) {
                permissionsManager.setStoragePermissionGranted(true)
            } else {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    fun handleStoragePermissionResult(
        isGranted: Boolean,
        shouldShowRationale: Boolean,
        galleryLauncher: ManagedActivityResultLauncher<String, Uri?>
    ) {
        scope.launch {
            if (isGranted) {
                permissionsManager.setStoragePermissionGranted(true)
                permissionsManager.setStoragePermissionDeniedPermanently(false)
                galleryLauncher.launch("image/*")
            } else {
                permissionsManager.setStoragePermissionGranted(false)

                if (!shouldShowRationale) {
                    permissionsManager.setStoragePermissionDeniedPermanently(true)
                    onError("Permiso de almacenamiento denegado permanentemente. Por favor, habilítalo en Configuración.")
                } else {
                    onError("Permiso de almacenamiento denegado")
                }
            }
        }
    }

    private fun launchCamera(cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>) {
        try {
            val photoFile = createImageFile()
            val photoUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                photoFile
            )
            currentPhotoUri = photoUri
            cameraLauncher.launch(photoUri)
        } catch (e: Exception) {
            onError("Error al abrir la cámara: ${e.message}")
        }
    }

    fun handleCameraResult(success: Boolean) {
        if (success && currentPhotoUri != null) {
            onImageSelected(currentPhotoUri!!)
        } else {
            onError("No se pudo capturar la imagen")
        }
        currentPhotoUri = null
    }

    fun handleGalleryResult(uri: Uri?) {
        if (uri != null) {
            onImageSelected(uri)
        } else {
            onError("No se seleccionó ninguna imagen")
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(null)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }
}