package com.fazq.rimayalert.features.auth.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.core.ui.theme.AuthColors
import com.fazq.rimayalert.features.auth.ui.state.LoginUiState


@Composable
fun LoginContentComponent(
    uiState: LoginUiState,
    authState: BaseUiState,
    snackbarHostState: SnackbarHostState,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRememberMeChange: (Boolean) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    val isLoading = authState is BaseUiState.LoadingState

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthColors.Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            MascotPlaceholder()

            Spacer(modifier = Modifier.height(40.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bienvenido de nuevo",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = AuthColors.TextPrimary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    AuthTextField(
                        value = "dev-test",
                        onValueChange = onUserNameChange,
                        label = "Nombre de usuario",
                        keyboardType = KeyboardType.Email,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AuthTextField(
                        value = "devtest",
                        onValueChange = onPasswordChange,
                        label = "Contraseña",
                        isPassword = true,
                        keyboardType = KeyboardType.Password,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = uiState.rememberMe,
                                onCheckedChange = onRememberMeChange,
                                colors = CheckboxDefaults.colors(
                                    checkedColor = AuthColors.Primary,
                                    uncheckedColor = AuthColors.BorderColor
                                ),
                                enabled = !isLoading
                            )
                            Text(
                                text = "Recordarme",
                                fontSize = 14.sp,
                                color = AuthColors.TextSecondary
                            )
                        }

                        TextButton(
                            onClick = onForgotPasswordClick,
                            enabled = !isLoading
                        ) {
                            Text(
                                text = "¿Olvidaste tu contraseña?",
                                fontSize = 14.sp,
                                color = AuthColors.Primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    AuthButton(
                        text = if (isLoading) "Iniciando sesión..." else "Iniciar Sesión",
                        onClick = onLoginClick,
                        enabled = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AuthFooterText(
                        normalText = "¿No tienes una cuenta? ",
                        clickableText = "Regístrate aquí",
                        onClick = onRegisterClick,
                        enabled = !isLoading
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }

}
