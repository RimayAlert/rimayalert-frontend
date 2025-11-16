package com.fazq.rimayalert.features.home.data.repository

import com.fazq.rimayalert.core.preferences.UserPreferencesManager
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.home.data.mapper.AssignCommunityRequestDTO
import com.fazq.rimayalert.features.home.data.repository.interfaces.CommunityInterface
import com.fazq.rimayalert.features.home.data.service.CommunityService
import com.fazq.rimayalert.features.home.domain.model.CommunityValidationResponseModel
import com.fazq.rimayalert.features.home.domain.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CommunityRepository @Inject constructor(
    private val apiService: CommunityService,
    private val userPreferencesManager: UserPreferencesManager
) : CommunityInterface {

    override suspend fun validateOrAssignCommunity(
        latitude: Double,
        longitude: Double
    ): DataState<CommunityValidationResponseModel> {
        val requestData = AssignCommunityRequestDTO(latitude, longitude)
        val response = apiService.validateOrAssignCommunity(requestData)
        var dataState: DataState<CommunityValidationResponseModel> = DataState.error("")
        response
            .catch { dataState = DataState.error(it.message ?: "Unknown Error") }
            .flowOn(Dispatchers.IO)
            .collect { responseState ->
                dataState = when (responseState) {
                    is DataState.Success -> {
                        val domainModel = responseState.data.toDomain()
                        if (responseState.code == 200) {
                            userPreferencesManager.saveHasCommunity(true)
                        }
                        DataState.success(domainModel)
                    }

                    is DataState.Error -> {
                        DataState.error(responseState.message)
                    }
                }
            }
        return dataState
    }

}