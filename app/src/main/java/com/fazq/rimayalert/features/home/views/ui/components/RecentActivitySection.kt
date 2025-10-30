package com.fazq.rimayalert.features.home.views.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fazq.rimayalert.core.ui.theme.TextPrimary
import com.fazq.rimayalert.features.home.domain.model.ActivityItemModel
import com.fazq.rimayalert.features.home.views.ui.screen.ActivityItem

@Composable
fun RecentActivitySection(
    activities: List<ActivityItemModel>,
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
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            TextButton(onClick = onRefresh) {
                Text(
                    text = "✨ Actualizado",
                    fontSize = 12.sp,
                    color = Color(0xFF5B6EF5)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (isRefreshing) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            activities.forEach { activity ->
                ActivityItem(
                    activity = activity,
                    onClick = { onActivityClick(activity.id) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}