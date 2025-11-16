package com.fazq.rimayalert.features.alerts.views.component.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.Dimensions
import com.fazq.rimayalert.core.ui.theme.TextSizes

@Composable
fun LocationSectionComponent(
    location: String,
    latitude: Double? = null,
    longitude: Double? = null,
    isLoadingLocation: Boolean = false,
    onEdit: () -> Unit,
    onUseMap: () -> Unit,
    onRefresh: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimensions.paddingSmall)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ubicación",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = TextSizes.title
                ),
                color = AppColors.textPrimary
            )

            IconButton(
                onClick = onRefresh,
                enabled = !isLoadingLocation,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Actualizar ubicación",
                    tint = if (isLoadingLocation)
                        AppColors.textSecondary.copy(alpha = 0.5f)
                    else
                        AppColors.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Dimensions.cornerRadiusMedium),
            colors = CardDefaults.cardColors(
                containerColor = AppColors.primary.copy(alpha = 0.05f)
            ),
            border = BorderStroke(1.dp, AppColors.primary.copy(alpha = 0.2f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.paddingMedium)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.paddingSmall)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(Dimensions.cornerRadiusMedium))
                            .background(AppColors.primary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ubicación",
                            tint = AppColors.primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // ⭐ MOSTRAR LOADING O UBICACIÓN
                        if (isLoadingLocation) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp,
                                    color = AppColors.primary
                                )
                                Text(
                                    text = "Obteniendo ubicación...",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        fontSize = TextSizes.body
                                    ),
                                    color = AppColors.textSecondary
                                )
                            }
                        } else {
                            Text(
                                text = location,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = TextSizes.body
                                ),
                                color = AppColors.textPrimary,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            // ⭐ COORDENADAS
                            if (latitude != null && longitude != null) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MyLocation,
                                        contentDescription = null,
                                        tint = AppColors.textSecondary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "${String.format("%.4f", latitude)}, ${
                                            String.format(
                                                "%.4f",
                                                longitude
                                            )
                                        }",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AppColors.textSecondary,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Dimensions.paddingMedium))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.paddingSmall)
                ) {
                    OutlinedButton(
                        onClick = onEdit,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(Dimensions.cornerRadiusMedium),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = AppColors.primary
                        ),
                        border = BorderStroke(1.dp, AppColors.primary.copy(alpha = 0.3f)),
                        contentPadding = PaddingValues(
                            horizontal = Dimensions.paddingSmall,
                            vertical = Dimensions.paddingSmall
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Editar",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            fontSize = 14.sp
                        )
                    }

                    Button(
                        onClick = onUseMap,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(Dimensions.cornerRadiusMedium),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.primary
                        ),
                        contentPadding = PaddingValues(
                            horizontal = Dimensions.paddingSmall,
                            vertical = Dimensions.paddingSmall
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Map,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Usar mapa",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.paddingSmall),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = AppColors.textSecondary.copy(alpha = 0.7f),
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "Tu ubicación ayudará a los servicios de emergencia a encontrarte rápidamente",
                style = MaterialTheme.typography.bodySmall,
                color = AppColors.textSecondary.copy(alpha = 0.8f),
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        }
    }
}