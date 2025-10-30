package com.fazq.rimayalert.features.home.views.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fazq.rimayalert.core.ui.theme.AuthColors.TextPrimary

@Composable
fun WeeklySummaryCard(
    alerts: Int,
    resolved: Int,
    pending: Int,
    averageTime: String,
    lastDays: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Resumen semanal",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "📅 Últimos $lastDays días",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatisticCard(
                    title = "Alertas",
                    value = alerts.toString(),
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFFE3F2FD)
                )

                StatisticCard(
                    title = "Resueltas",
                    value = resolved.toString(),
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFFE8F5E9)
                )
            }


            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatisticCard(
                    title = "Pendientes",
                    value = pending.toString(),
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFFFFF3E0)
                )
                StatisticCard(
                    title = "Tiempo medio",
                    value = averageTime,
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFFF3E5F5)
                )
            }
        }
    }
}