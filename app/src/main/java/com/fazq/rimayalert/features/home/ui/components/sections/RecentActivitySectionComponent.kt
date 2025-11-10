package com.fazq.rimayalert.features.home.ui.components.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.Dimensions
import com.fazq.rimayalert.features.home.domain.model.IncidentModel
import com.fazq.rimayalert.features.home.ui.components.empty.EmptyActivityStateComponent
import com.fazq.rimayalert.features.home.ui.components.items.IncidentActivityItemComponent

@Composable
fun RecentActivitySectionComponent(
    activities: List<IncidentModel>,
    onActivityClick: (String) -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Actividad reciente",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.textPrimary
            )

            TextButton(
                onClick = onRefresh,
                enabled = !isRefreshing
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    tint = AppColors.primary,
                    modifier = Modifier.size(Dimensions.iconSizeSmall)
                )
                Spacer(modifier = Modifier.width(Dimensions.gapTiny))
                Text(
                    text = if (isRefreshing) "Actualizando..." else "Actualizado",
                    fontSize = 13.sp,
                    color = AppColors.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimensions.gapMedium))

        if (isRefreshing) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = AppColors.primary,
                    strokeWidth = Dimensions.strokeWidthThin
                )
            }
        } else {
            if (activities.isEmpty()) {
                EmptyActivityStateComponent()
            } else {
                activities.forEach { incident ->
                    IncidentActivityItemComponent(
                        incident = incident,
                        onClick = { onActivityClick(incident.id.toString()) }
                    )
                    Spacer(modifier = Modifier.height(Dimensions.gapCompact))
                }
            }
        }
    }
}