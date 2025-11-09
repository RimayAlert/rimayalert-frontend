package com.fazq.rimayalert.features.home.data.service

import androidx.annotation.WorkerThread
import com.fazq.rimayalert.core.functions.flowResponse
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.utils.StringUtils
import com.fazq.rimayalert.features.common.interfaces.ErrorDao
import com.fazq.rimayalert.features.home.data.api.HomeApiClient
import com.fazq.rimayalert.features.home.data.mapper.IncidentDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeService @Inject constructor(
    private val api: HomeApiClient,
    private val stringUtils: StringUtils,
    private val errorDao: ErrorDao
) {

    @WorkerThread
    suspend fun getIncidentInfo(): Flow<DataState<List<IncidentDTO>>> =
        flowResponse(api.getIncidentInfo(), "getIncidentInfo", errorDao, stringUtils)

}