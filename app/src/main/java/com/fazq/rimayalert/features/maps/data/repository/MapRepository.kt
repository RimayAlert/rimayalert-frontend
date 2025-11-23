package com.fazq.rimayalert.features.maps.data.repository

import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.maps.data.repository.interfaces.MapInterface
import com.fazq.rimayalert.features.maps.data.service.MapService
import com.fazq.rimayalert.features.maps.domain.model.MapDataModel
import com.fazq.rimayalert.features.maps.domain.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MapRepository @Inject constructor(
    private val api: MapService
) : MapInterface {

    override suspend fun mapDetailUser(): DataState<MapDataModel> {
        val response = api.detailMap()
        var dataState: DataState<MapDataModel> = DataState.error("")
        response
            .catch { dataState = DataState.error(it.message ?: "Error") }
            .flowOn(Dispatchers.IO)
            .collect { responseState ->
                dataState = when (responseState) {
                    is DataState.Success -> {
                        val data = responseState.data
                        val dataModel = data.toDomain()
                        DataState.Success(dataModel, responseState.code)
                    }

                    is DataState.Error -> {
                        DataState.Error(responseState.message, responseState.code)
                    }
                }
            }
        return dataState
    }

}