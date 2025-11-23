package com.fazq.rimayalert.features.maps.domain.usecase

import com.fazq.rimayalert.core.di.IoDispatcher
import com.fazq.rimayalert.core.functions.NetworkFunction
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.maps.data.repository.MapRepository
import com.fazq.rimayalert.features.maps.domain.model.MapDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MapUseCase @Inject constructor(
    private val repository: MapRepository,
    private val networkFunction: NetworkFunction,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getMapIncidents(): DataState<MapDataModel> {
        return withContext(ioDispatcher) {
            when (networkFunction.hasInternetConnection()) {
                true -> repository.mapDetailUser()
                false -> DataState.Error("No hay conexión a internet", 400)
            }
        }
    }
}