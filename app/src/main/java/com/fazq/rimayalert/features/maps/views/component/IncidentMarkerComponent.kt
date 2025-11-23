package com.fazq.rimayalert.features.maps.views.component

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint // Usar android.graphics.Paint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb // Importar para convertir Color de Compose
import androidx.compose.ui.platform.LocalContext
import com.fazq.rimayalert.features.maps.domain.model.MapIncidentModel
import com.fazq.rimayalert.features.maps.views.utils.CustomMarkerUtils
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun IncidentMarkerComponent(
    incident: MapIncidentModel,
    isOwn: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val position = LatLng(incident.latitude ?: return, incident.longitude ?: return)

    val markerIcon = remember(incident.id, isOwn, isSelected) {
        val iconResId = CustomMarkerUtils.getIncidentIcon(incident.incidentTypeName)
        val backgroundColor = if (isOwn) {
            Color(0xFF3B82F6)
        } else {
            CustomMarkerUtils.getSeverityColor(incident.severityLevel)
        }

        CustomMarkerUtils.createCustomMarker(
            context = context,
            iconResId = iconResId,
            backgroundColor = backgroundColor,
            isSelected = isSelected
        )
    }

    Marker(
        state = MarkerState(position = position),
        title = incident.title,
        snippet = "${incident.incidentTypeName} - ${incident.statusName}",
        icon = markerIcon,
        onClick = {
            onClick()
            true
        }
    )
}

@Composable
fun CurrentLocationMarker(location: LatLng) {
    val markerIcon = remember {
        val size = 80
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint().apply {
            isAntiAlias = true
        }

        val centerX = size / 2f
        val centerY = size / 2f

        // Círculo exterior (pulso) - Azul transparente
        paint.apply {
            color = android.graphics.Color.parseColor("#4D3B82F6")
            style = Paint.Style.FILL
        }
        canvas.drawCircle(centerX, centerY, size / 2f - 5f, paint)

        // Círculo medio - Azul sólido
        paint.apply {
            color = android.graphics.Color.parseColor("#FF3B82F6")
            style = Paint.Style.FILL
        }
        canvas.drawCircle(centerX, centerY, size / 3f, paint)

        // Borde blanco
        paint.apply {
            color = android.graphics.Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }
        canvas.drawCircle(centerX, centerY, size / 3f, paint)

        // Punto central blanco
        paint.apply {
            color = android.graphics.Color.WHITE
            style = Paint.Style.FILL
        }
        canvas.drawCircle(centerX, centerY, size / 6f, paint)

        BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    Marker(
        state = MarkerState(position = location),
        title = "Mi ubicación",
        icon = markerIcon,
        anchor = Offset(0.5f, 0.5f)
    )
}