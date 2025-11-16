package com.fazq.rimayalert.features.profile.views.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileAvatarComponent(
    userName: String,
    modifier: Modifier = Modifier,
    size: Int = 80
) {
    val initial = userName.firstOrNull()?.uppercaseChar()?.toString() ?: "?"
    val backgroundColor = generateColorFromName(userName)

    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            fontSize = (size * 0.4).sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

private fun generateColorFromName(name: String): Color {
    val colors = listOf(
        Color(0xFF6366F1),
        Color(0xFF8B5CF6),
        Color(0xFFEC4899),
        Color(0xFFEF4444),
        Color(0xFFF59E0B),
        Color(0xFF10B981),
        Color(0xFF3B82F6),
        Color(0xFF06B6D4),
    )
    val hash = name.hashCode()
    return colors[Math.abs(hash) % colors.size]
}