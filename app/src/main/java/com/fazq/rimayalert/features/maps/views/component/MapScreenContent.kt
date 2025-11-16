package com.fazq.rimayalert.features.maps.views.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fazq.rimayalert.core.ui.theme.Dimensions
import com.fazq.rimayalert.features.maps.views.event.MapsEvent
import com.fazq.rimayalert.features.maps.views.state.MapsUiState

@Composable
fun MapScreenContent(
    modifier: Modifier = Modifier,
    mapsUiState: MapsUiState,
    onEvent: (MapsEvent) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimensions.paddingDefault)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MapHeader()

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
                    when {
                        !mapsUiState.hasLocationPermission -> {
                            LocationPermissionRequired(
                                onRequestPermission = { onEvent(MapsEvent.RequestLocationPermission) }
                            )
                        }

                        else -> {
                            MapView(
                                currentLocation = mapsUiState.currentLocation,
                                incidents = mapsUiState.incidents,
                                selectedIncidentId = mapsUiState.selectedIncident?.id,
                                hasLocationPermission = mapsUiState.hasLocationPermission,
                                onIncidentClick = { incidentId ->
                                    onEvent(MapsEvent.OnIncidentSelected(incidentId))
                                },
                                onMapReady = { isReady ->
                                    onEvent(MapsEvent.OnMapReady(isReady))
                                },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp))
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 12.dp, end = 12.dp),
                                contentAlignment = Alignment.TopEnd
                            ) {
                                MapControls(
                                    onMyLocationClick = { onEvent(MapsEvent.OnMyLocationClick) },
                                    onRefreshClick = { onEvent(MapsEvent.OnRefreshClick) }
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 12.dp, bottom = 12.dp),
                                contentAlignment = Alignment.BottomStart
                            ) {
                                MapLegend()
                            }

                            if (mapsUiState.isLoadingLocation) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    HandleMapDialogs(
        dialogState = mapsUiState.dialogState,
        onEvent = onEvent
    )
}