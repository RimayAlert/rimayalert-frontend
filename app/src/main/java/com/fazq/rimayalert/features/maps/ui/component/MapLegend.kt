package com.fazq.rimayalert.features.maps.ui.component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MapLegend() {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Leyenda",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )

            LegendItem(
                color = MaterialTheme.colorScheme.error,
                label = "Alta prioridad"
            )
            LegendItem(
                color = MaterialTheme.colorScheme.tertiary,
                label = "Media prioridad"
            )
            LegendItem(
                color = MaterialTheme.colorScheme.primary,
                label = "Baja prioridad"
            )
        }
    }
}