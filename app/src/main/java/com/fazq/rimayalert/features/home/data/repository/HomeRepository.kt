package com.fazq.rimayalert.features.home.data.repository

import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.home.data.repository.interfaces.HomeInterface
import com.fazq.rimayalert.features.home.data.service.HomeService
import com.fazq.rimayalert.features.home.domain.model.IncidentModel
import com.fazq.rimayalert.features.home.domain.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: HomeService
) : HomeInterface {


    override suspend fun getIncident(): DataState<List<IncidentModel>> {
        val response = apiService.getIncidentInfo()
        var dataState: DataState<List<IncidentModel>> = DataState.error("")
        response
            .catch { dataState = DataState.error(it.message ?: "Unknown Error") }
            .flowOn(Dispatchers.IO)
            .collect { responseState ->
                dataState = when (responseState) {
                    is DataState.Success -> {
                        val incidents = responseState.data.map { it.toDomain() }
                        DataState.success(incidents)
                    }

                    is DataState.Error -> {
                        DataState.error(responseState.message)
                    }
                }
            }
        return dataState
    }
}