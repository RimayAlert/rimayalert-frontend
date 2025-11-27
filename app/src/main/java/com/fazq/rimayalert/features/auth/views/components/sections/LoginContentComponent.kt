package com.fazq.rimayalert.features.auth.views.components.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.Dimensions
import com.fazq.rimayalert.core.ui.theme.TextSizes
import com.fazq.rimayalert.features.auth.views.components.AuthFooterTextComponent
import com.fazq.rimayalert.features.auth.views.components.MascotPlaceholderComponent
import com.fazq.rimayalert.features.auth.views.components.buttons.AuthButtonComponent
import com.fazq.rimayalert.features.auth.views.state.LoginUiState


@Composable
fun LoginContentComponent(
    uiState: LoginUiState,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onPermissionRequired: () -> Unit
) {
    val isLoading = uiState.isLoading
    val canAccess = uiState.hasLocationPermission

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
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = Dimensions.gapTiny
            ) {
                Column(
                    modifier = Modifier.padding(Dimensions.paddingDefault),
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

                    ModernTextField(
                        value = uiState.userName,
                        onValueChange = onUserNameChange,
                        label = "Nombre de usuario",
                        error = uiState.usernameError,
                        leadingIcon = Icons.Default.Person,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(Dimensions.gapMedium))

                    ModernTextField(
                        value = uiState.password,
                        onValueChange = onPasswordChange,
                        label = "Contraseña",
                        error = uiState.passwordError,
                        isPassword = true,
                        keyboardType = KeyboardType.Password,
                        leadingIcon = Icons.Default.Lock,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(Dimensions.gapCompact))

//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.End
//                    ) {
//                        TextButton(
//                            onClick = onForgotPasswordClick,
//                            enabled = !isLoading,
//                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
//                        ) {
//                            Text(
//                                text = "¿Olvidaste tu contraseña?",
//                                fontSize = 14.sp,
//                                color = MaterialTheme.colorScheme.primary,
//                                fontWeight = FontWeight.Medium
//                            )
//                        }
//                    }

                    Spacer(modifier = Modifier.height(Dimensions.gapLarge))

                    AuthButtonComponent(
                        text = if (isLoading) "Iniciando sesión..." else "Iniciar Sesión",
                        onClick = {
                            if (canAccess) {
                                onLoginClick()
                            } else {
                                onPermissionRequired()
                            }
                        },
                        enabled = !isLoading,
                        isLoading = isLoading
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


@Composable
fun ModernTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    onFocusLost: (() -> Unit)? = null,
    error: String? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = if (error != null) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                }
            },
            trailingIcon = if (isPassword) {
                {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        enabled = enabled
                    ) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        )
                    }
                }
            } else null,
            isError = error != null,
            enabled = enabled,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = if (isPassword) ImeAction.Done else ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    onFocusLost?.invoke()
                    focusManager.moveFocus(FocusDirection.Down)
                },
                onDone = {
                    onFocusLost?.invoke()
                    focusManager.clearFocus()
                }
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color(0xFFE0E0E0),
                errorBorderColor = MaterialTheme.colorScheme.error,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = Color(0xFF757575),
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = Color(0xFFF8F9FA),
                unfocusedContainerColor = Color(0xFFFAFAFA),
                errorContainerColor = Color(0xFFFFF5F5),
                disabledBorderColor = Color(0xFFE0E0E0),
                disabledContainerColor = Color(0xFFF5F5F5),
                disabledLabelColor = Color(0xFF9E9E9E)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (isFocused && !focusState.isFocused) {
                        onFocusLost?.invoke()
                    }
                    isFocused = focusState.isFocused
                }
        )

        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}
