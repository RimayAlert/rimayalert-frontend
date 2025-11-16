package com.fazq.rimayalert.features.profile.views.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fazq.rimayalert.core.states.DialogState
import com.fazq.rimayalert.core.ui.components.dialogs.ConfirmationDialogComponent
import com.fazq.rimayalert.core.ui.components.dialogs.ErrorDialogComponent
import com.fazq.rimayalert.core.ui.components.dialogs.SuccessDialogComponent
import com.fazq.rimayalert.features.profile.views.event.ProfileEvent
import com.fazq.rimayalert.features.profile.views.state.ProfileUiState

@Composable
fun ProfileContentComponent(
    profileUiState: ProfileUiState,
    paddingValues: PaddingValues,
    onEvent: (ProfileEvent) -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(profileUiState.shouldNavigateToLogin) {
        if (profileUiState.shouldNavigateToLogin) {
            onNavigateToLogin()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        ProfileHeaderComponent(
            userName = profileUiState.userName,
            memberSince = profileUiState.memberSince
        )

        ProfileDividerComponent()

        ProfileSectionComponent(title = "Datos Personales") {
            ProfileMenuItemComponent(
                icon = Icons.Outlined.DateRange,
                title = "Miembro desde",
                subtitle = profileUiState.memberSince,
                showArrow = false
            )

            ProfileMenuItemComponent(
                icon = Icons.Outlined.Email,
                title = "Correo Electrónico",
                subtitle = profileUiState.userEmail,
                showArrow = false
            )

            ProfileMenuItemComponent(
                icon = Icons.Filled.Notifications,
                title = "Notificaciones Activas",
                showSwitch = true,
                switchValue = profileUiState.notificationsEnabled,
                onSwitchChange = { onEvent(ProfileEvent.OnNotificationToggle) }
            )

            ProfileMenuItemComponent(
                icon = Icons.Outlined.Home,
                title = "Comunidad Asignada",
                subtitle = profileUiState.communityAssigned,
                onClick = { onEvent(ProfileEvent.OnCommunityClick) }
            )
        }

        ProfileDividerComponent()

        ProfileSectionComponent(title = "Configuración") {
            ProfileMenuItemComponent(
                icon = Icons.Filled.Lock,
                title = "Cambiar Contraseña",
                onClick = { onEvent(ProfileEvent.OnChangePasswordClick) }
            )

            ProfileMenuItemComponent(
                icon = Icons.Filled.Info,
                title = "Acerca de la App",
                onClick = { onEvent(ProfileEvent.OnAboutAppClick) }
            )

            ProfileMenuItemComponent(
                icon = Icons.Outlined.AccountCircle,
                title = "Cerrar Sesión",
                iconTint = Color(0xFFEF4444),
                showArrow = false,
                onClick = { onEvent(ProfileEvent.OnLogoutClick) }
            )
        }

        ProfileDividerComponent()

        ProfileMapButtonComponent(
            onClick = { onEvent(ProfileEvent.OnOpenIncidentsMapClick) },
            modifier = Modifier.padding(16.dp)
        )
    }


    when (val dialog = profileUiState.dialogState) {
        is DialogState.Confirmation -> {
            ConfirmationDialogComponent(
                openDialog = true,
                title = dialog.title,
                message = dialog.message,
                confirmText = "Confirmar",
                cancelText = "Cancelar",
                onConfirm = dialog.onConfirm,
                onDismiss = { onEvent(ProfileEvent.OnDismissDialog) }
            )
        }

        is DialogState.Error -> {
            ErrorDialogComponent(
                openDialog = true,
                title = dialog.title,
                message = dialog.message,
                onDismiss = { onEvent(ProfileEvent.OnDismissDialog) }
            )
        }

        is DialogState.Success -> {
            SuccessDialogComponent(
                openDialog = true,
                title = dialog.title,
                message = dialog.message,
                onDismiss = { onEvent(ProfileEvent.OnDismissDialog) }
            )
        }

        is DialogState.None -> {}
    }
}