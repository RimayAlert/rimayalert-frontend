package com.fazq.rimayalert.features.home.views.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fazq.rimayalert.core.ui.theme.TextPrimary
import com.fazq.rimayalert.features.home.domain.model.ActivityItemModel
import com.fazq.rimayalert.features.home.domain.model.AlertStatus

@Composable
fun ActivityItem(
    activity: ActivityItemModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono según tipo
            val (icon, iconColor) = when (activity.status) {
                AlertStatus.EMERGENCY -> "🚨" to Color(0xFFEF4444)
                AlertStatus.WARNING -> "🔔" to Color(0xFFF59E0B)
                AlertStatus.SUCCESS -> "✅" to Color(0xFF10B981)
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = iconColor.copy(alpha = 0.1f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = activity.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
                Text(
                    text = activity.subtitle,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            // Badge de tiempo o estado
            val (badgeText, badgeColor) = when (activity.status) {
                AlertStatus.EMERGENCY -> "Ahora" to Color(0xFFEF4444)
                AlertStatus.WARNING -> activity.time to Color(0xFF5B6EF5)
                AlertStatus.SUCCESS -> "OK" to Color(0xFF10B981)
            }

            Surface(
                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                color = badgeColor
            ) {
                Text(
                    text = badgeText,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}