package com.fazq.rimayalert.features.home.views.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getSeverityColor(severity: String?): Color {
    return when (severity?.lowercase()) {
        "alta", "high" -> Color(0xFFEF4444)
        "media", "medium" -> Color(0xFFF59E0B)
        "baja", "low" -> Color(0xFF10B981)
        else -> Color(0xFF8B5CF6)
    }
}

fun formatRelativeTime(timestamp: String): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val dateTime = LocalDateTime.parse(timestamp, formatter)
        val now = LocalDateTime.now()

        val minutes = Duration.between(dateTime, now).toMinutes()
        val hours = minutes / 60
        val days = hours / 24

        when {
            minutes < 1 -> "Ahora"
            minutes < 60 -> "Hace ${minutes}m"
            hours < 24 -> "Hace ${hours}h"
            days < 7 -> "Hace ${days}d"
            days < 30 -> "Hace ${days / 7}sem"
            days < 365 -> "Hace ${days / 30}mes"
            else -> "Hace ${days / 365}a"
        }
    } catch (e: Exception) {
        timestamp
    }
}

fun getSeverityGradient(severity: String?): Brush {
    return when (severity?.lowercase()) {
        "alta", "high" -> Brush.linearGradient(
            colors = listOf(Color(0xFFEF4444), Color(0xFFDC2626))
        )
        "media", "medium" -> Brush.linearGradient(
            colors = listOf(Color(0xFFF59E0B), Color(0xFFD97706))
        )
        "baja", "low" -> Brush.linearGradient(
            colors = listOf(Color(0xFF10B981), Color(0xFF059669))
        )
        else -> Brush.linearGradient(
            colors = listOf(Color(0xFF8B5CF6), Color(0xFF7C3AED))
        )
    }
}

@Composable
fun ChicSeverityBadge(severity: String) {
    val (icon, backgroundColor, textColor) = when (severity.lowercase()) {
        "alta", "high" -> Triple(
            Icons.Default.Warning,
            Color(0xFFFEE2E2),
            Color(0xFFDC2626)
        )
        "media", "medium" -> Triple(
            Icons.Default.Error,
            Color(0xFFFEF3C7),
            Color(0xFFD97706)
        )
        "baja", "low" -> Triple(
            Icons.Default.Info,
            Color(0xFFDCFCE7),
            Color(0xFF16A34A)
        )
        else -> Triple(
            Icons.Default.Circle,
            Color(0xFFF3F4F6),
            Color(0xFF6B7280)
        )
    }

    Surface(
        shape = RoundedCornerShape(10.dp),
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
                text = severity.replaceFirstChar { it.uppercase() },
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                letterSpacing = 0.3.sp
            )
        }
    }
}