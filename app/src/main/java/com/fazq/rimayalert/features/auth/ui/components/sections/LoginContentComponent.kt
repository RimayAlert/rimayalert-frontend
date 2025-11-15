package com.fazq.rimayalert.features.auth.ui.components.sections

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.Dimensions
import com.fazq.rimayalert.core.ui.theme.TextSizes
import com.fazq.rimayalert.features.auth.ui.components.AuthFooterTextComponent
import com.fazq.rimayalert.features.auth.ui.components.AuthTextFieldComponent
import com.fazq.rimayalert.features.auth.ui.components.MascotPlaceholderComponent
import com.fazq.rimayalert.features.auth.ui.components.buttons.AuthButtonComponent
import com.fazq.rimayalert.features.auth.ui.state.LoginUiState


@Composable
fun LoginContentComponent(
    uiState: LoginUiState,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRememberMeChange: (Boolean) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    val isLoading = uiState.isLoading

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.backgroundLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Dimensions.paddingComfortable),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(Dimensions.gapHuge))

            MascotPlaceholderComponent()

            Spacer(modifier = Modifier.height(Dimensions.gapXXLarge))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(Dimensions.cornerRadiusLarge),
                color = AppColors.surfaceLight,
                shadowElevation = Dimensions.elevationMedium
            ) {
                Column(
                    modifier = Modifier.padding(Dimensions.paddingComfortable),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bienvenido de nuevo",
                        fontSize = TextSizes.headline,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.textPrimary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(Dimensions.gapXLarge))

                    AuthTextFieldComponent(
//                        value = "dev-test",
                        value = uiState.userName,
                        onValueChange = onUserNameChange,
                        label = "Nombre de usuario",
                        keyboardType = KeyboardType.Email,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(Dimensions.gapMedium))

                    AuthTextFieldComponent(
//                        value = "devtest",
                        value = uiState.password,
                        onValueChange = onPasswordChange,
                        label = "Contraseña",
                        isPassword = true,
                        keyboardType = KeyboardType.Password,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(Dimensions.gapCompact))

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
                                    checkedColor = AppColors.checkboxChecked,
                                    uncheckedColor = AppColors.checkboxUnchecked
                                ),
                                enabled = !isLoading
                            )
                            Text(
                                text = "Recordarme",
                                fontSize = TextSizes.medium,
                                color = AppColors.secondary
                            )
                        }

                        TextButton(
                            onClick = onForgotPasswordClick,
                            enabled = !isLoading
                        ) {
                            Text(
                                text = "¿Olvidaste tu contraseña?",
                                fontSize = TextSizes.medium,
                                color = AppColors.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(Dimensions.gapLarge))

                    AuthButtonComponent(
                        text = if (isLoading) "Iniciando sesión..." else "Iniciar Sesión",
                        onClick = onLoginClick,
                        enabled = true
                    )

                    Spacer(modifier = Modifier.height(Dimensions.gapMedium))

                    AuthFooterTextComponent(
                        normalText = "¿No tienes una cuenta? ",
                        clickableText = "Regístrate aquí",
                        onClick = onRegisterClick,
                        enabled = !isLoading
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.gapXXLarge))
        }
    }

}
