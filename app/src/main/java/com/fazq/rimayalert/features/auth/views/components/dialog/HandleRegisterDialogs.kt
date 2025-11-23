package com.fazq.rimayalert.features.auth.views.components.dialogs

import androidx.compose.runtime.Composable
import com.fazq.rimayalert.core.states.DialogState
import com.fazq.rimayalert.core.ui.components.dialogs.ConfirmationDialogComponent
import com.fazq.rimayalert.core.ui.components.dialogs.ErrorDialogComponent
import com.fazq.rimayalert.core.ui.components.dialogs.SuccessDialogComponent

@Composable
fun HandleRegisterDialogs(
    dialogState: DialogState,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    when (dialogState) {
        is DialogState.Error -> {
            ErrorDialogComponent(
                openDialog = true,
                title = dialogState.title,
                message = dialogState.message,
                onDismiss = onDismiss
            )
        }

        is DialogState.Success -> {
            SuccessDialogComponent(
                openDialog = true,
                title = dialogState.title,
                message = dialogState.message,
                buttonText = "Continuar",
                onDismiss = {
                    onDismiss()
                    onSuccess() // Navegar al login o home
                }
            )
        }

        is DialogState.Confirmation -> {
            ConfirmationDialogComponent(
                openDialog = true,
                title = dialogState.title,
                message = dialogState.message,
                confirmText = "Aceptar",
                cancelText = "Cancelar",
                onConfirm = {
                    dialogState.onConfirm()
                    onDismiss()
                },
                onDismiss = onDismiss
            )
        }

        DialogState.None -> {
            // No mostrar nada
        }
    }
}