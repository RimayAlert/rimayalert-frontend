package com.fazq.rimayalert.features.alerts.data.service

import androidx.annotation.WorkerThread
import com.fazq.rimayalert.core.functions.flowResponse
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.utils.StringUtils
import com.fazq.rimayalert.features.alerts.data.api.AlertApiClient
import com.fazq.rimayalert.features.alerts.data.mapper.AlertResponseDTO
import com.fazq.rimayalert.features.common.interfaces.ErrorDao
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AlertService @Inject constructor(
    private val apiAlert: AlertApiClient,
    private val stringUtils: StringUtils,
    private val errorDao: ErrorDao
) {
    @WorkerThread
    suspend fun createAlert(
        data: RequestBody,
        image: MultipartBody.Part?
    ): Flow<DataState<AlertResponseDTO>> =
        flowResponse(apiAlert.createAlert(data, image), "createAlert", errorDao, stringUtils)

}