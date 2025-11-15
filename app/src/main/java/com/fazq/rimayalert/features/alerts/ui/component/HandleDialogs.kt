package com.fazq.rimayalert.features.alerts.ui.component

import androidx.compose.runtime.Composable
import com.fazq.rimayalert.core.states.DialogState
import com.fazq.rimayalert.core.ui.components.dialogs.ConfirmationDialogComponent
import com.fazq.rimayalert.core.ui.components.dialogs.ErrorDialogComponent
import com.fazq.rimayalert.core.ui.components.dialogs.SuccessDialogComponent
import com.fazq.rimayalert.features.alerts.ui.event.AlertEvent

@Composable
fun HandleDialogs(
    dialogState: DialogState,
    onEvent: (AlertEvent) -> Unit
) {
    when (dialogState) {
        is DialogState.Error -> {
            ErrorDialogComponent(
                openDialog = true,
                title = dialogState.title,
                message = dialogState.message,
                onDismiss = { onEvent(AlertEvent.DismissDialog) }
            )
        }

        is DialogState.Success -> {
            SuccessDialogComponent(
                openDialog = true,
                title = dialogState.title,
                message = dialogState.message,
                onDismiss = { onEvent(AlertEvent.DismissDialog) }
            )
        }

        is DialogState.Confirmation -> {
            ConfirmationDialogComponent(
                openDialog = true,
                title = dialogState.title,
                message = dialogState.message,
                confirmText = "Enviar",
                cancelText = "Cancelar",
                onConfirm = dialogState.onConfirm,
                onDismiss = { onEvent(AlertEvent.DismissDialog) }
            )
        }

        DialogState.None -> { /* No mostrar diálogo */
        }
    }
}