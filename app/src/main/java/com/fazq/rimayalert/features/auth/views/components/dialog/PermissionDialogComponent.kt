package com.fazq.rimayalert.features.auth.views.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun PermissionDialogComponent(
    openDialog: Boolean,
    permanentlyDenied: Boolean,
    onRetry: () -> Unit,
    onOpenSettings: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!openDialog) return

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Text(
                "📍",
                style = MaterialTheme.typography.displaySmall
            )
        },
        title = {
            Text(
                text = "Se requiere acceso a la ubicación",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Text(
                text = "Para continuar necesitamos permisos de ubicación. Puedes reintentarlo o abrir los ajustes.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            if (!permanentlyDenied) {
                Button(
                    onClick = onRetry,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Intentar de nuevo")
                }
            }

        },
        dismissButton = {
            TextButton(
                onClick = onOpenSettings,
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Ajustes")
            }
        }
    )
}
