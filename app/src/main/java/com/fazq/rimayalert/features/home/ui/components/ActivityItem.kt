package com.fazq.rimayalert.features.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fazq.rimayalert.features.home.domain.model.ActivityItemModel
import com.fazq.rimayalert.features.home.domain.model.ActivityStatus
import com.fazq.rimayalert.features.home.domain.model.ActivityStyleModel

@Composable
fun ActivityItem(
    activity: ActivityItemModel,
    onClick: () -> Unit
) {
    val style = when (activity.status) {
        ActivityStatus.ACTIVE -> ActivityStyleModel(
            icon = Icons.Default.ErrorOutline,
            bgColor = Color.White,
            iconColor = Color(0xFFEF4444),
            statusBgColor = Color(0xFFFEE2E2),
            statusTextColor = Color(0xFFDC2626)
        )
        ActivityStatus.IN_PROGRESS -> ActivityStyleModel(
            icon = Icons.Default.Timelapse,
            bgColor = Color.White,
            iconColor = Color(0xFFF59E0B),
            statusBgColor = Color(0xFFFEF3C7),
            statusTextColor = Color(0xFFD97706)
        )
        ActivityStatus.RESOLVED -> ActivityStyleModel(
            icon = Icons.Default.CheckCircleOutline,
            bgColor = Color.White,
            iconColor = Color(0xFF10B981),
            statusBgColor = Color(0xFFD1FAE5),
            statusTextColor = Color(0xFF059669)
        )
    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = style.bgColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(style.iconColor.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = style.icon,
                        contentDescription = null,
                        tint = style.iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = activity.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1E293B)
                    )
                    Text(
                        text = activity.subtitle,
                        fontSize = 13.sp,
                        color = Color(0xFF64748B)
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = style.statusBgColor
            ) {
                Text(
                    text = activity.time,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = style.statusTextColor,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}