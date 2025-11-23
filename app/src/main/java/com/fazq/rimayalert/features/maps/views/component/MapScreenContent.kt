package com.fazq.rimayalert.features.maps.views.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fazq.rimayalert.core.ui.components.ModernLoaderComponent
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MapHeaderWithStats(
                totalIncidents = mapsUiState.totalCount,
                myIncidents = mapsUiState.myIncidents.size,
                otherIncidents = mapsUiState.otherIncidents.size,
                radiusKm = mapsUiState.radiusKm
            )

            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
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
                                myIncidents = mapsUiState.myIncidents,
                                otherIncidents = mapsUiState.otherIncidents,
                                selectedIncidentId = mapsUiState.selectedIncident?.id,
                                hasLocationPermission = mapsUiState.hasLocationPermission,
                                radiusKm = mapsUiState.radiusKm,
                                onIncidentClick = { incidentId ->
                                    onEvent(MapsEvent.OnIncidentSelected(incidentId))
                                },
                                onMapReady = { isReady ->
                                    onEvent(MapsEvent.OnMapReady(isReady))
                                },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(24.dp))
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.TopEnd
                            ) {
                                MapControls(
                                    onMyLocationClick = { onEvent(MapsEvent.OnMyLocationClick) },
                                    onRefreshClick = { onEvent(MapsEvent.OnRefreshClick) },
                                    isRefreshing = mapsUiState.isLoadingIncidents
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.BottomStart
                            ) {
                                MapLegend()
                            }
                        }
                    }
                }
            }
        }

        ModernLoaderComponent(
            isLoading = mapsUiState.isLoadingIncidents,
            message = "Cargando incidentes cercanos...",
            fullScreen = false
        )
    }

    // Bottom Sheet para detalles del incidente
    IncidentBottomSheet(
        incident = mapsUiState.selectedIncident,
        onDismiss = { onEvent(MapsEvent.DismissError) }
    )
}