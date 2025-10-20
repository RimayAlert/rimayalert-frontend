package com.fazq.rimayalert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.fazq.rimayalert.core.ui.theme.RimayAlertTheme
import com.fazq.rimayalert.features.auth.ui.LoginScreen
import com.fazq.rimayalert.features.auth.views.ui.main.screen.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RimayAlertTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentScreen by remember { mutableStateOf("login") }

                    when (currentScreen) {
                        "login" -> LoginScreen(
                            onLoginClick = { email, password ->
                                println("Login: $email")
                            },
                            onRegisterClick = {
                                currentScreen = "register"
                            },
                            onForgotPasswordClick = {
                                println("Recuperar contraseña")
                            }
                        )
                        "register" -> RegisterScreen(
                            onRegisterClick = { formData ->
                                println("Registro: ${formData.username}, ${formData.email}")
                            },
                            onLoginClick = {
                                currentScreen = "login"
                            },
                            onBackClick = {
                                currentScreen = "login"
                            },
                            onTermsClick = {
                                println("Abrir términos de uso")
                            }
                        )
                    }
                }
            }
        }
    }
}