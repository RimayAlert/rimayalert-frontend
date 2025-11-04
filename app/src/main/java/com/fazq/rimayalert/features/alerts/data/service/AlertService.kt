package com.fazq.rimayalert.features.alerts.data.service

import androidx.annotation.WorkerThread
import com.fazq.rimayalert.core.functions.flowResponse
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.utils.StringUtils
import com.fazq.rimayalert.features.alerts.data.api.AlertApiClient
import com.fazq.rimayalert.features.alerts.data.mapper.AlertRequestDTO
import com.fazq.rimayalert.features.alerts.data.mapper.AlertResponseDTO
import com.fazq.rimayalert.features.common.interfaces.ErrorDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlertService @Inject constructor(
    private val apiAlert: AlertApiClient,
    private val stringUtils: StringUtils,
    private val errorDao: ErrorDao
) {
    @WorkerThread
    suspend fun createAlert(data: AlertRequestDTO): Flow<DataState<AlertResponseDTO>> =
        flowResponse(apiAlert.createAlert(data), "createAlert", errorDao, stringUtils)

}