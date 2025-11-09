package com.fazq.rimayalert.features.home.data.repository

import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.home.data.mapper.AssignCommunityRequestDTO
import com.fazq.rimayalert.features.home.data.repository.interfaces.CommunityInterface
import com.fazq.rimayalert.features.home.data.service.CommunityService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CommunityRepository @Inject constructor(
    private val apiService: CommunityService
) : CommunityInterface {


    override suspend fun checkCommunityStatus(): DataState<Boolean> {
        val response = apiService.checkCommunity()
        var dataState: DataState<Boolean> = DataState.error("")
        response
            .catch { dataState = DataState.error(it.message ?: "Unknown Error") }
            .flowOn(Dispatchers.IO)
            .collect { responseState ->
                dataState = when (responseState) {
                    is DataState.Success -> {
                        if (responseState.data.hasCommunity) {
                            DataState.success(true)
                        } else {
                            DataState.success(false)
                        }
                    }

                    is DataState.Error -> {
                        DataState.error(responseState.message)
                    }
                }
            }
        return dataState
    }

    override suspend fun assignCommunity(latitude: Double, longitude: Double): DataState<Boolean> {
        val requestData = AssignCommunityRequestDTO(
            latitude = latitude,
            longitude = longitude
        )
        val response = apiService.assignCommunity(requestData)
        var dataState: DataState<Boolean> = DataState.error("")
        response
            .catch { dataState = DataState.error(it.message ?: "Unknown Error") }
            .flowOn(Dispatchers.IO)
            .collect { responseState ->
                dataState = when (responseState) {
                    is DataState.Success -> {
                        if (responseState.data.hasCommunity) {
                            DataState.success(true)
                        } else {
                            DataState.success(false)
                        }
                    }

                    is DataState.Error -> {
                        DataState.error(responseState.message)
                    }
                }
            }
        return dataState
    }

}