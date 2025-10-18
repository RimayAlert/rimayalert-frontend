package com.fazq.rimayalert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.fazq.rimayalert.core.ui.theme.RimayAlertTheme
import com.fazq.rimayalert.features.auth.ui.LoginScreen

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
                    LoginScreen(
                        onLoginClick = { email, password ->
                            // TODO: Implementar lógica de login
                            println("Login: $email")
                        },
                        onRegisterClick = {
                            // TODO: Navegar a pantalla de registro
                            println("Navegar a registro")
                        },
                        onForgotPasswordClick = {
                            // TODO: Navegar a recuperar contraseña
                            println("Recuperar contraseña")
                        }
                    )
                }
            }
        }
    }
}