package com.fazq.rimayalert.features.home.domain.usecase

import com.fazq.rimayalert.core.di.IoDispatcher
import com.fazq.rimayalert.core.functions.NetworkFunction
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.home.data.repository.HomeRepository
import com.fazq.rimayalert.features.home.domain.model.IncidentModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val repository: HomeRepository,
    private val networkFunction: NetworkFunction,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): DataState<List<IncidentModel>> {
        return withContext(ioDispatcher) {
            when (networkFunction.hasInternetConnection()) {
                true -> {
                    when (val response = repository.getIncident()) {
                        is DataState.Success -> DataState.success(response.data)
                        is DataState.Error -> DataState.error(response.message)
                    }
                }

                false -> DataState.error("Sin conexión a internet")
            }
        }
    }

}