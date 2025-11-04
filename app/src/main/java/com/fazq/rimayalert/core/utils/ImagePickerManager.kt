package com.fazq.rimayalert.core.utils

import android.content.Context
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import java.io.File

class ImagePickerManager(
    private val context: Context,
    private val onImageSelected: (String) -> Unit,
    private val onError: (String) -> Unit
) {
    private var currentPhotoFile: File? = null

    fun launchCamera(cameraLauncher: ActivityResultLauncher<Uri>) {
        val photoFile = ImageUtils.createImageFile(context)
        photoFile?.let {
            currentPhotoFile = it
            val photoUri = ImageUtils.getImageUri(context, it)
            cameraLauncher.launch(photoUri)
        } ?: run {
            onError("Error al crear archivo de imagen")
        }
    }

    fun handleCameraResult(success: Boolean) {
        if (success && currentPhotoFile != null) {
            val uri = Uri.fromFile(currentPhotoFile)
            onImageSelected(uri.toString())
        } else {
            ImageUtils.deleteImageFile(currentPhotoFile)
            currentPhotoFile = null
        }
    }

    fun handleGalleryResult(uri: Uri?) {
        uri?.let {
            onImageSelected(it.toString())
        }
    }

    fun handleCameraPermission(
        isGranted: Boolean,
        cameraLauncher: ActivityResultLauncher<Uri>
    ) {
        if (isGranted) {
            launchCamera(cameraLauncher)
        } else {
            onError("Permiso de cámara denegado")
        }
    }

    fun handleStoragePermission(
        isGranted: Boolean,
        galleryLauncher: ActivityResultLauncher<String>
    ) {
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            onError("Permiso de almacenamiento denegado")
        }
    }

    fun cleanup() {
        currentPhotoFile = null
    }
}