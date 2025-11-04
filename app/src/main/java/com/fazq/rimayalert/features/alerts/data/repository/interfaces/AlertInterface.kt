package com.fazq.rimayalert.features.alerts.data.repository.interfaces

import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.alerts.domain.model.AlertModel

interface AlertInterface {
    suspend fun createAlert(alertModel: AlertModel): DataState<String>
}