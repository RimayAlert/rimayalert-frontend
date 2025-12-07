package com.fazq.rimayalert.features.auth.views.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun RegisterCheckboxesComponent(
    acceptTerms: Boolean,
    onAcceptTermsChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var showTermsDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8F9FA),
        border = BorderStroke(
            width = 1.dp,
            color = if (acceptTerms) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            else Color(0xFFE0E0E0)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onAcceptTermsChange(!acceptTerms) }
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = acceptTerms,
                onCheckedChange = onAcceptTermsChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = Color(0xFF9E9E9E),
                    checkmarkColor = Color.White
                ),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Acepto los ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF424242),
                    fontSize = 14.sp
                )
                Text(
                    text = "términos de uso",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        showTermsDialog = true
                    }
                )
            }
        }
    }

    if (showTermsDialog) {
        TermsOfUseDialog(onDismiss = { showTermsDialog = false })
    }
}

@Composable
private fun TermsOfUseDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        )
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Términos de Uso",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar",
                                tint = Color.White
                            )
                        }
                    }
                }

                // Content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp)
                ) {
                    TermSection(
                        title = "1. Aceptación de Términos",
                        content = "Al usar RimayAlert, aceptas estos términos de uso. Esta aplicación está diseñada para alertas comunitarias de emergencia y uso responsable."
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    TermSection(
                        title = "2. Uso de la Aplicación",
                        content = "RimayAlert es una plataforma de alertas comunitarias. Los usuarios se comprometen a:\n\n" +
                                "• Publicar solo alertas verídicas y relevantes\n" +
                                "• No compartir información falsa o alarmista\n" +
                                "• Respetar la privacidad de otros usuarios\n" +
                                "• Usar la aplicación de manera responsable"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    TermSection(
                        title = "3. Responsabilidad del Usuario",
                        content = "Los usuarios son responsables del contenido que publican. RimayAlert no se hace responsable por:\n\n" +
                                "• Información incorrecta publicada por usuarios\n" +
                                "• Decisiones tomadas basándose en alertas\n" +
                                "• Daños indirectos derivados del uso de la app"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    TermSection(
                        title = "4. Privacidad y Datos",
                        content = "Nos comprometemos a proteger tu información personal. Los datos de ubicación y alertas se usan únicamente para el funcionamiento de la aplicación y mejorar la seguridad comunitaria."
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    TermSection(
                        title = "5. Conducta Prohibida",
                        content = "Está prohibido:\n\n" +
                                "• Crear pánico innecesario con alertas falsas\n" +
                                "• Usar la app para acosar o amenazar\n" +
                                "• Publicar contenido ofensivo o ilegal\n" +
                                "• Interferir con el funcionamiento de la app"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    TermSection(
                        title = "6. Modificaciones",
                        content = "RimayAlert se reserva el derecho de modificar estos términos en cualquier momento. Los cambios serán notificados a través de la aplicación."
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Última actualización: Noviembre 2025",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF757575),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Footer Button
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Entendido",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun TermSection(
    title: String,
    content: String
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF424242),
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}