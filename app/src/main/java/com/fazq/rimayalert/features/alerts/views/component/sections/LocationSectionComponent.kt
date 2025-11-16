package com.fazq.rimayalert.features.alerts.views.component.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LocationSectionComponent(
    location: String,
    onEdit: () -> Unit,
    onUseMap: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Ubicación",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = Color(0xFF424242)
            )
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            AssistChip(
                onClick = {},
                label = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFF757575)
                        )
                        Text(location)
                    }
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color(0xFFF5F5F5),
                    labelColor = Color(0xFF424242)
                )
            )

            OutlinedButton(
                onClick = onEdit,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF424242)
                ),
                border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("Editar")
            }

            OutlinedButton(
                onClick = onUseMap,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF424242)
                ),
                border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("Usar mapa")
            }
        }

        Text(
            text = "No compartas datos personales. Solo puntos de referencia.",
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFF9E9E9E)
            )
        )
    }
}