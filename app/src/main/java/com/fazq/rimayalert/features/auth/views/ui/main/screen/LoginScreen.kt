package com.fazq.rimayalert.features.auth.views.ui.main.screen

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fazq.rimayalert.core.constants.SavedStateKeys
import com.fazq.rimayalert.core.ui.theme.AuthColors
import com.fazq.rimayalert.core.utils.SavedStateUtils
import com.fazq.rimayalert.features.auth.views.ui.main.components.AppLogo
import com.fazq.rimayalert.features.auth.views.ui.main.components.AuthButton
import com.fazq.rimayalert.features.auth.views.ui.main.components.AuthTextField
import com.fazq.rimayalert.features.auth.views.ui.main.components.MascotPlaceholder

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginClick: (email: String, password: String) -> Unit = { _, _ -> },
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var showWelcome by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    SavedStateUtils.read<String>(navController, SavedStateKeys.USERNAME) { username ->
        email = username
        showWelcome = true
    }

    LaunchedEffect(showWelcome) {
        if (showWelcome) {
            snackbarHostState.showSnackbar(
                message = "¡Registro exitoso! Ahora inicia sesión con $email",
                duration = SnackbarDuration.Short
            )
            showWelcome = false
        }
    }

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

            // Logo de la app (muy tenue)
            AppLogo()

            Spacer(modifier = Modifier.height(40.dp))

            // Card principal
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
                    // Espacio para la mascota
                    MascotPlaceholder()

                    Spacer(modifier = Modifier.height(24.dp))

                    // Título
                    Text(
                        text = "Bienvenido de nuevo",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = AuthColors.TextPrimary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Campo de correo electrónico
                    AuthTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Correo electrónico o nombre de usuario",
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de contraseña
                    AuthTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Contraseña",
                        isPassword = true,
                        keyboardType = KeyboardType.Password
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Recordarme y olvidaste contraseña
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = AuthColors.Primary,
                                    uncheckedColor = AuthColors.BorderColor
                                )
                            )
                            Text(
                                text = "Recordarme",
                                fontSize = 14.sp,
                                color = AuthColors.TextSecondary
                            )
                        }

                        TextButton(
                            onClick = onForgotPasswordClick
                        ) {
                            Text(
                                text = "¿Olvidaste tu contraseña?",
                                fontSize = 14.sp,
                                color = AuthColors.Primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón de inicio de sesión
                    AuthButton(
                        text = "Iniciar Sesión",
                        onClick = { onLoginClick(email, password) },
                        enabled = email.isNotBlank() && password.isNotBlank()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Registro
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "¿No tienes una cuenta? ",
                            fontSize = 14.sp,
                            color = AuthColors.TextSecondary
                        )
                        TextButton(
                            onClick = onRegisterClick,
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Regístrate aquí",
                                fontSize = 14.sp,
                                color = AuthColors.Primary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}