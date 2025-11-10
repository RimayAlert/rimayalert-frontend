package com.fazq.rimayalert.features.home.ui.components.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.Dimensions
import com.fazq.rimayalert.features.home.domain.model.IncidentModel

@Composable
fun IncidentActivityItemComponent(
    incident: IncidentModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(Dimensions.cornerRadiusMedium),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.surfaceLight
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimensions.elevationLow
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.paddingDefault),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.gapCompact)
        ) {
            SeverityIconComponent(severity = incident.severityLevel.toString())

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimensions.gapTiny)
            ) {
                Text(
                    text = incident.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.textPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = incident.description,
                    fontSize = 14.sp,
                    color = AppColors.textSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                if (incident.occurredAt != null) {
                    Text(
                        text = formatIncidentTime(incident.occurredAt),
                        fontSize = 12.sp,
                        color = AppColors.textHint,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            SeverityBadgeComponent(severity = incident.severityLevel.toString())
        }
    }
}

fun formatIncidentTime(occurredAt: String): String {
    return try {
        "Hace 2 horas"
    } catch (e: Exception) {
        occurredAt
    }
}