package com.fazq.rimayalert.features.alerts.ui.component.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fazq.rimayalert.core.ui.theme.AppColors
import com.fazq.rimayalert.core.ui.theme.Dimensions
import com.fazq.rimayalert.core.ui.theme.TextSizes
import com.fazq.rimayalert.features.alerts.ui.component.AlertHeaderComponent
import com.fazq.rimayalert.features.alerts.ui.component.AlertTypeSelectorComponent
import com.fazq.rimayalert.features.alerts.ui.state.AlertUiState


@Composable
fun AlertsContentComponent(
    modifier: Modifier = Modifier,
    uiState: AlertUiState,
    onTypeSelected: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onLocationEdit: () -> Unit,
    onUseMap: () -> Unit,
    onUploadImage: () -> Unit,
    onOpenCamera: () -> Unit,
    onSendAlert: () -> Unit,
    onRemoveImage: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimensions.paddingDefault)
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(Dimensions.cornerRadiusExtraLarge),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = Dimensions.elevationLow
            )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = Dimensions.paddingMediumLarge)
                        .padding(
                            top = Dimensions.paddingMediumLarge,
                            bottom = 88.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.paddingMediumLarge)
                ) {
                    AlertHeaderComponent()

                    AlertTypeSelectorComponent(
                        selectedType = uiState.selectedType,
                        onTypeSelected = onTypeSelected
                    )

                    LocationSectionComponent(
                        location = uiState.location,
                        onEdit = onLocationEdit,
                        onUseMap = onUseMap
                    )

                    DescriptionInputComponent(
                        description = uiState.description,
                        onDescriptionChange = onDescriptionChanged
                    )

                    ImageUploadSectionComponent(
                        imageUri = uiState.imageUri,
                        onUpload = onUploadImage,
                        onCamera = onOpenCamera,
                        onRemoveImage = onRemoveImage
                    )
                }

                Button(
                    onClick = onSendAlert,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.paddingMediumLarge)
                        .align(Alignment.BottomCenter),
                    shape = RoundedCornerShape(Dimensions.cornerRadiusLarge),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.primary
                    ),
                    enabled = !uiState.isLoading
                ) {
                    Text(
                        text = if (uiState.isLoading) "Enviando..." else "Enviar alerta",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = TextSizes.body
                        ),
                        color = Color.White,
                        modifier = Modifier.padding(vertical = Dimensions.paddingCompact)
                    )
                }
            }
        }
    }
}