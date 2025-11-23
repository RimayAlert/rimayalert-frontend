package com.fazq.rimayalert.features.maps.views.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object CustomMarkerUtils {

    fun createCustomMarker(
        context: Context,
        iconResId: Int,
        backgroundColor: Color,
        isSelected: Boolean = false
    ): BitmapDescriptor {
        val size = if (isSelected) 120 else 100
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Dibujar círculo de fondo
        val paint = Paint().apply {
            isAntiAlias = true
            color = backgroundColor.toArgb()
            style = Paint.Style.FILL
        }

        val centerX = size / 2f
        val centerY = size / 2f
        val radius = size / 2f - 10f

        canvas.drawCircle(centerX, centerY, radius, paint)

        // Borde blanco
        paint.apply {
            color = android.graphics.Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = if (isSelected) 8f else 6f
        }
        canvas.drawCircle(centerX, centerY, radius, paint)

        // Dibujar icono
        val drawable = ContextCompat.getDrawable(context, iconResId)?.mutate()
        drawable?.let {
            it.colorFilter = PorterDuffColorFilter(
                android.graphics.Color.WHITE,
                PorterDuff.Mode.SRC_IN
            )
            val iconSize = (size * 0.5f).toInt()
            val left = (size - iconSize) / 2
            val top = (size - iconSize) / 2
            it.setBounds(left, top, left + iconSize, top + iconSize)
            it.draw(canvas)
        }

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun getIncidentIcon(incidentTypeName: String?): Int {
        return when (incidentTypeName?.lowercase()) {
            "robo" -> android.R.drawable.ic_lock_lock // Usar tus propios iconos
            "asalto" -> android.R.drawable.ic_dialog_alert
            "accidente" -> android.R.drawable.ic_dialog_info
            "incendio" -> android.R.drawable.ic_delete
            "emergencia", "medico" -> android.R.drawable.ic_menu_help
            else -> android.R.drawable.ic_menu_info_details
        }
    }

    fun getSeverityColor(severityLevel: Int?): Color {
        return when (severityLevel) {
            3 -> Color(0xFFEF4444) // Alta - Rojo
            2 -> Color(0xFFF59E0B) // Media - Naranja
            1 -> Color(0xFFFCD34D) // Baja - Amarillo
            else -> Color(0xFF8B5CF6) // Desconocido - Púrpura
        }
    }
}