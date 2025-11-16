package com.fazq.rimayalert.features.alerts.views.component.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.Dimensions

@Composable
fun ImageUploadSectionComponent(
    imageUri: String?,
    onUpload: () -> Unit,
    onCamera: () -> Unit,
    onRemoveImage: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimensions.gapMedium),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Adjuntar imagen (opcional)",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = AppColors.textPrimary
            ),
            modifier = Modifier.align(Alignment.Start)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(Dimensions.cornerRadiusMedium))
                .background(Color(0xFFF5F5F5))
                .border(
                    width = 1.dp,
                    color = AppColors.borderColor,
                    shape = RoundedCornerShape(Dimensions.cornerRadiusMedium)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (imageUri == null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(Dimensions.gapCompact),
                    modifier = Modifier.padding(Dimensions.paddingComfortable)
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Color(0xFFBDBDBD)
                    )
                    Text(
                        text = "Espacio para imagen",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = AppColors.textSecondary
                        )
                    )
                    Text(
                        text = "Sube evidencia si es seguro hacerlo.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = AppColors.textHint
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUri.toUri())
                            .crossfade(true)
                            .build(),
                        contentDescription = "Imagen adjunta",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    onRemoveImage?.let { removeAction ->
                        IconButton(
                            onClick = removeAction,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(Dimensions.paddingCompact)
                                .size(32.dp)
                                .background(
                                    color = Color.Black.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(16.dp)
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Eliminar imagen",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimensions.gapMedium),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = onUpload,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = AppColors.textPrimary
                ),
                border = BorderStroke(1.dp, AppColors.borderColor),
                contentPadding = PaddingValues(vertical = Dimensions.gapMedium)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.gapCompact),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Upload,
                        contentDescription = null,
                        modifier = Modifier.size(Dimensions.iconSizeSmall)
                    )
                    Text("Subir")
                }
            }

            OutlinedButton(
                onClick = onCamera,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = AppColors.textPrimary
                ),
                border = BorderStroke(1.dp, AppColors.borderColor),
                contentPadding = PaddingValues(vertical = Dimensions.gapMedium)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.gapCompact),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        modifier = Modifier.size(Dimensions.iconSizeSmall)
                    )
                    Text("Cámara")
                }
            }
        }
    }
}