package com.fazq.rimayalert.features.alerts.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.fazq.rimayalert.core.ui.theme.Dimensions
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
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 88.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
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
                    onCamera = onOpenCamera
                )
            }

            Button(
                onClick = onSendAlert,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(56.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2962FF)
                )
            ) {
                Text(
                    text = "Enviar alerta",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.White
                )
            }
        }
    }
}