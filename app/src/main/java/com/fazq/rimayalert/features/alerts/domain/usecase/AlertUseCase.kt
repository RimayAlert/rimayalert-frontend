package com.fazq.rimayalert.features.alerts.domain.usecase

import com.fazq.rimayalert.core.di.IoDispatcher
import com.fazq.rimayalert.core.functions.NetworkFunction
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.alerts.data.repository.AlertRepository
import com.fazq.rimayalert.features.alerts.domain.model.AlertModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AlertUseCase @Inject constructor(
    private val repository: AlertRepository,
    private val networkFunction: NetworkFunction,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun createAlert(alertModel: AlertModel): DataState<String> {
        return withContext(ioDispatcher) {
            when (networkFunction.hasInternetConnection()) {
                true -> repository.createAlert(alertModel)
                false -> DataState.error("No hay conexión a Internet. Por favor, verifica tu conexión e intenta nuevamente.")
            }
        }
    }
}
