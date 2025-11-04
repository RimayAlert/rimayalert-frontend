package com.fazq.rimayalert.features.alerts.data.repository

import com.fazq.rimayalert.core.preferences.UserPreferencesManager
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.utils.StringUtils
import com.fazq.rimayalert.core.utils.TokenManager
import com.fazq.rimayalert.features.alerts.data.repository.interfaces.AlertInterface
import com.fazq.rimayalert.features.alerts.data.service.AlertService
import com.fazq.rimayalert.features.alerts.domain.model.AlertModel
import com.fazq.rimayalert.features.alerts.domain.model.toRequestDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AlertRepository @Inject constructor(
    private val api: AlertService,
    private val tokenManager: TokenManager,
    private val stringUtils: StringUtils,
    private val userPreferencesManager: UserPreferencesManager
) : AlertInterface {


    override suspend fun createAlert(alertModel: AlertModel): DataState<String> {
        val response = api.createAlert(alertModel.toRequestDTO())
        var dataState: DataState<String> = DataState.error("")
        response
            .catch { dataState = DataState.error(it.message ?: "Error") }
            .flowOn(Dispatchers.IO)
            .collect { responseState ->
                dataState = when (responseState) {
                    is DataState.Success -> {
                        DataState.success(responseState.data.message)
                    }

                    is DataState.Error -> {
                        DataState.error(responseState.message)
                    }
                }
            }
        return dataState
    }

}