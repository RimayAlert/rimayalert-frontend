package com.fazq.rimayalert.features.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import com.fazq.rimayalert.core.ui.theme.Dimensions
import com.fazq.rimayalert.core.ui.theme.FontWeights
import com.fazq.rimayalert.core.ui.theme.TextSizes

@Composable
fun WeeklySummaryCard(
    alerts: Int,
    resolved: Int,
    pending: Int,
    averageTime: String,
    lastDays: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                Dimensions.elevationMedium,
                RoundedCornerShape(Dimensions.cornerRadiusExtraLarge)
            ),
        shape = RoundedCornerShape(Dimensions.cornerRadiusExtraLarge),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(Dimensions.paddingMediumLarge)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Resumen semanal",
                    fontSize = TextSizes.title,
                    fontWeight = FontWeights.bold,
                    color = Color(0xFF1E293B)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.gapTiny)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(Dimensions.iconSizeSmall)
                    )
                    Text(
                        text = "Últimos $lastDays días",
                        fontSize = TextSizes.small,
                        color = Color(0xFF64748B)
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.gapMediumLarge))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.gapCompact)
            ) {
                StatisticCard(
                    icon = Icons.Default.Notifications,
                    title = "Alertas",
                    value = alerts.toString(),
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFFDCFCE7),
                    iconColor = Color(0xFF16A34A),
                    iconBackground = Color(0xFFF0FDF4)
                )

                StatisticCard(
                    icon = Icons.Default.CheckCircle,
                    title = "Resueltas",
                    value = resolved.toString(),
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFFDCFCE7),
                    iconColor = Color(0xFF16A34A),
                    iconBackground = Color(0xFFF0FDF4)
                )
            }

            Spacer(modifier = Modifier.height(Dimensions.gapMediumLarge))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.gapCompact)
            ) {
                StatisticCard(
                    icon = Icons.Default.PendingActions,
                    title = "Pendientes",
                    value = pending.toString(),
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFFFEF3C7),
                    iconColor = Color(0xFFEAB308),
                    iconBackground = Color(0xFFFEFCE8)
                )
                StatisticCard(
                    icon = Icons.Default.AccessTime,
                    title = "Tiempo medio",
                    value = averageTime,
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFFE9D5FF),
                    iconColor = Color(0xFF9333EA),
                    iconBackground = Color(0xFFFAF5FF)
                )
            }
        }
    }
}