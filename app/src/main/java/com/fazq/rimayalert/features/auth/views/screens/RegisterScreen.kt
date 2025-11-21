package com.fazq.rimayalert.features.auth.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fazq.rimayalert.core.ui.components.AuthTopBarComponent
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.Dimensions
import com.fazq.rimayalert.features.auth.views.components.AuthFooterTextComponent
import com.fazq.rimayalert.features.auth.views.components.RegisterCheckboxesComponent
import com.fazq.rimayalert.features.auth.views.components.buttons.AuthButtonComponent
import com.fazq.rimayalert.features.auth.views.components.dialogs.HandleRegisterDialogs
import com.fazq.rimayalert.features.auth.views.components.sections.RegisterFormFieldsComponent
import com.fazq.rimayalert.features.auth.views.event.RegisterEvent
import com.fazq.rimayalert.features.auth.views.viewmodel.RegisterUserViewModel


@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    registerUserViewModel: RegisterUserViewModel = hiltViewModel()
) {
    val uiState by registerUserViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.backgroundLight)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AuthTopBarComponent(
                title = "Crear cuenta",
                onBackClick = onBackClick
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Dimensions.paddingDefault)
                    .padding(
                        top = Dimensions.paddingDefault,
                        bottom = Dimensions.paddingExtraSpacious
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                        RegisterFormFieldsComponent(
                            registerData = uiState.registerData,
                            onDataChange = {
                                registerUserViewModel.onEvent(RegisterEvent.OnRegisterDataChange(it))
                            },
                            displayNameError = uiState.displayNameError
                        )

                        Spacer(modifier = Modifier.height(Dimensions.gapMedium))

                        RegisterCheckboxesComponent(
                            acceptTerms = uiState.registerData.acceptTerms,
                            onAcceptTermsChange = {
                                registerUserViewModel.onEvent(RegisterEvent.OnAcceptTermsChange(it))
                            },
                            onTermsClick = onTermsClick
                        )

                        Spacer(modifier = Modifier.height(Dimensions.gapXLarge))

                        AuthButtonComponent(
                            text = "Crear Cuenta",
                            onClick = {
                                registerUserViewModel.onEvent(RegisterEvent.OnRegisterClick)
                            },
                            enabled = !uiState.isLoading &&
                                    uiState.registerData.username.isNotBlank() &&
                                    uiState.registerData.email.isNotBlank() &&
                                    uiState.registerData.displayName.isNotBlank() &&
                                    uiState.registerData.password.isNotBlank() &&
                                    uiState.registerData.confirmPassword.isNotBlank() &&
                                    uiState.registerData.acceptTerms,
                            isLoading = uiState.isLoading
                        )

                        if (uiState.isLoading) {
                            Spacer(modifier = Modifier.height(Dimensions.gapMedium))
                            CircularProgressIndicator()
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        AuthFooterTextComponent(
                            normalText = "¿Ya tienes una cuenta? ",
                            clickableText = "Inicia sesión",
                            onClick = onLoginClick
                        )
                    }
                }
            }
        }
    }

    HandleRegisterDialogs(
        dialogState = uiState.dialogState,
        onDismiss = { registerUserViewModel.onEvent(RegisterEvent.OnDismissDialog) },
        onSuccess = onRegisterSuccess
    )
}