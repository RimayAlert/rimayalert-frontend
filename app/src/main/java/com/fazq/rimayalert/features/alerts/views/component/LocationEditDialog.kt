import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.fazq.rimayalert.core.ui.theme.AppColors

@Composable
fun LocationEditDialog(
    currentAddress: String,
    currentLatitude: Double?,
    currentLongitude: Double?,
    onDismiss: () -> Unit,
    onConfirm: (address: String, latitude: Double?, longitude: Double?) -> Unit
) {
    var address by remember { mutableStateOf(currentAddress) }
    var latitudeText by remember {
        mutableStateOf(currentLatitude?.toString() ?: "")
    }
    var longitudeText by remember {
        mutableStateOf(currentLongitude?.toString() ?: "")
    }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Editar Ubicación",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = AppColors.textPrimary
                )

                Text(
                    text = "Ingresa manualmente la dirección y coordenadas",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.textSecondary
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = {
                        address = it
                        showError = false
                    },
                    label = { Text("Dirección") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.primary,
                        unfocusedBorderColor = AppColors.primary.copy(alpha = 0.3f),
                        focusedLabelColor = AppColors.primary
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = AppColors.primary
                        )
                    },
                    singleLine = false,
                    maxLines = 2,
                    minLines = 1
                )

                // Coordenadas opcionales
                Text(
                    text = "Coordenadas (Opcional)",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = AppColors.textPrimary
                )

                // Campo Latitud
                OutlinedTextField(
                    value = latitudeText,
                    onValueChange = {
                        latitudeText = it
                        showError = false
                    },
                    label = { Text("Latitud") },
                    placeholder = { Text("Ej: -2.1894") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.primary,
                        unfocusedBorderColor = AppColors.primary.copy(alpha = 0.3f),
                        focusedLabelColor = AppColors.primary
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )

                // Campo Longitud
                OutlinedTextField(
                    value = longitudeText,
                    onValueChange = {
                        longitudeText = it
                        showError = false
                    },
                    label = { Text("Longitud") },
                    placeholder = { Text("Ej: -79.8862") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.primary,
                        unfocusedBorderColor = AppColors.primary.copy(alpha = 0.3f),
                        focusedLabelColor = AppColors.primary
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )

                // Mensaje de error
                if (showError) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = Color(0xFFD32F2F),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFD32F2F)
                        )
                    }
                }

                // Nota informativa
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColors.primary.copy(alpha = 0.05f)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = AppColors.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Las coordenadas son opcionales. Si no las ingresas, solo se guardará la dirección.",
                            style = MaterialTheme.typography.bodySmall,
                            color = AppColors.textSecondary,
                            fontSize = 11.sp,
                            lineHeight = 14.sp
                        )
                    }
                }

                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = AppColors.textSecondary
                        ),
                        border = BorderStroke(1.dp, AppColors.textSecondary.copy(alpha = 0.3f))
                    ) {
                        Text(
                            text = "Cancelar",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                    Button(
                        onClick = {
                            // Validación
                            when {
                                address.isBlank() -> {
                                    showError = true
                                    errorMessage = "La dirección es obligatoria"
                                }

                                latitudeText.isNotBlank() && latitudeText.toDoubleOrNull() == null -> {
                                    showError = true
                                    errorMessage = "Latitud inválida"
                                }

                                longitudeText.isNotBlank() && longitudeText.toDoubleOrNull() == null -> {
                                    showError = true
                                    errorMessage = "Longitud inválida"
                                }

                                (latitudeText.isNotBlank() && longitudeText.isBlank()) ||
                                        (latitudeText.isBlank() && longitudeText.isNotBlank()) -> {
                                    showError = true
                                    errorMessage = "Debes ingresar ambas coordenadas o ninguna"
                                }

                                else -> {
                                    val lat = latitudeText.toDoubleOrNull()
                                    val lon = longitudeText.toDoubleOrNull()
                                    onConfirm(address.trim(), lat, lon)
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.primary
                        )
                    ) {
                        Text(
                            text = "Guardar",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}