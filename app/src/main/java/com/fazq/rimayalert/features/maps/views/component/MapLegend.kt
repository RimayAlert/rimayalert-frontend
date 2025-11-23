package com.fazq.rimayalert.features.maps.views.component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MapLegend() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Leyenda",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF374151)
            )

            LegendItem(
                color = Color(0xFF3B82F6),
                label = "Mis incidentes"
            )
            LegendItem(
                color = Color(0xFFEF4444),
                label = "Severidad Alta"
            )
            LegendItem(
                color = Color(0xFFF59E0B),
                label = "Severidad Media"
            )
            LegendItem(
                color = Color(0xFFFCD34D),
                label = "Severidad Baja"
            )
        }
    }
}