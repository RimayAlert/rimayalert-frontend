package com.fazq.rimayalert.core.ui.components.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialogComponent(
    openDialog: Boolean,
    title: String = "Error de autenticación",
    message: String = "No fue posible validar sus credenciales.",
    onDismiss: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Aceptar")
                }
            },
            title = {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
            },
            text = {
                Text(text = message, style = MaterialTheme.typography.bodyMedium)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.ErrorOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        )
    }
}