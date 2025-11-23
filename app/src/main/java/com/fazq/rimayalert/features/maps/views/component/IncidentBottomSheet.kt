package com.fazq.rimayalert.features.maps.views.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fazq.rimayalert.features.home.views.utils.formatRelativeTime
import com.fazq.rimayalert.features.maps.domain.model.MapIncidentModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentBottomSheet(
    incident: MapIncidentModel?,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (incident != null) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            containerColor = Color.White,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(4.dp)
                            .background(
                                color = Color(0xFFE5E7EB),
                                shape = RoundedCornerShape(2.dp)
                            )
                    )
                }
            }
        ) {
            IncidentDetailsContent(
                incident = incident,
                onDismiss = onDismiss
            )
        }
    }
}

@Composable
fun IncidentDetailsContent(
    incident: MapIncidentModel,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
    ) {
        // Header con tipo y severidad
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = incident.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827),
                    lineHeight = 30.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Badge de tipo
                    IncidentTypeBadge(typeName = incident.incidentTypeName)

                    // Badge de severidad
                    SeverityBadge(
                        level = incident.severityLevel,
                        statusName = incident.statusName
                    )
                }
            }

            // Icono de cerrar
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color(0xFFF3F4F6),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cerrar",
                    tint = Color(0xFF6B7280)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Descripción
        Text(
            text = "Descripción",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF374151)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = incident.description,
            fontSize = 15.sp,
            color = Color(0xFF6B7280),
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Información adicional
        InfoRow(
            icon = Icons.Default.LocationOn,
            label = "Dirección",
            value = incident.address ?: "No especificada"
        )

        Spacer(modifier = Modifier.height(12.dp))

        InfoRow(
            icon = Icons.Default.AccessTime,
            label = "Reportado",
            value = formatRelativeTime(incident.reportedAt)
        )

        if (incident.occurredAt != null) {
            Spacer(modifier = Modifier.height(12.dp))

            InfoRow(
                icon = Icons.Default.DateRange,
                label = "Ocurrió",
                value = formatRelativeTime(incident.occurredAt)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Badge si es propio
        if (incident.isOwn) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFDCFCE7)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF16A34A)
                    )
                    Text(
                        text = "Este incidente fue reportado por ti",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF16A34A)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botones de acción
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { /* Compartir */ },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF6366F1)
                ),
                border = BorderStroke(1.5.dp, Color(0xFF6366F1))
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Compartir")
            }

            Button(
                onClick = { /* Ver en mapa */ },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6366F1)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ver ruta")
            }
        }
    }
}

@Composable
fun IncidentTypeBadge(typeName: String?) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFEEF2FF)
    ) {
        Text(
            text = typeName ?: "Sin tipo",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF6366F1),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun SeverityBadge(level: Int?, statusName: String?) {
    val (backgroundColor, textColor, icon) = when (level) {
        3 -> Triple(Color(0xFFFEE2E2), Color(0xFFDC2626), Icons.Default.Warning)
        2 -> Triple(Color(0xFFFEF3C7), Color(0xFFD97706), Icons.Default.Error)
        1 -> Triple(Color(0xFFFEF9C3), Color(0xFFCA8A04), Icons.Default.Info)
        else -> Triple(Color(0xFFF3F4F6), Color(0xFF6B7280), Icons.Default.Circle)
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = statusName ?: "Desconocido",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}

@Composable
fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = Color(0xFFF3F4F6),
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF6366F1),
                modifier = Modifier.size(20.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF9CA3AF)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF374151)
            )
        }
    }
}