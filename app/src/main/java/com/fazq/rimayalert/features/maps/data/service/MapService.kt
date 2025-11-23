package com.fazq.rimayalert.features.maps.data.service

import androidx.annotation.WorkerThread
import com.fazq.rimayalert.core.functions.flowResponse
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.utils.StringUtils
import com.fazq.rimayalert.features.common.interfaces.ErrorDao
import com.fazq.rimayalert.features.maps.data.api.MapApiClient
import com.fazq.rimayalert.features.maps.data.mapper.MapDataDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MapService @Inject constructor(
    private val apiMap: MapApiClient,
    private val stringUtils: StringUtils,
    private val errorDao: ErrorDao
) {
    @WorkerThread
    suspend fun detailMap(): Flow<DataState<MapDataDto>> =
        flowResponse(apiMap.getMapIncidents(), "detailMap", errorDao, stringUtils)

}