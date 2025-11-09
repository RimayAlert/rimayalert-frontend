package com.fazq.rimayalert.features.home.data.service

import androidx.annotation.WorkerThread
import com.fazq.rimayalert.core.functions.flowResponse
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.utils.StringUtils
import com.fazq.rimayalert.features.common.interfaces.ErrorDao
import com.fazq.rimayalert.features.home.data.api.CommunityApiClient
import com.fazq.rimayalert.features.home.data.mapper.AssignCommunityRequestDTO
import com.fazq.rimayalert.features.home.data.mapper.CommunityValidationResponseDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommunityService @Inject constructor(
    private val api: CommunityApiClient,
    private val stringUtils: StringUtils,
    private val errorDao: ErrorDao
) {
    @WorkerThread
    suspend fun checkCommunity(): Flow<DataState<CommunityValidationResponseDTO>> =
        flowResponse(api.checkCommunityStatus(), "checkCommunityStatus", errorDao, stringUtils)

    @WorkerThread
    suspend fun assignCommunity(data: AssignCommunityRequestDTO): Flow<DataState<CommunityValidationResponseDTO>> =
        flowResponse(api.assignCommunity(data), "assignCommunity", errorDao, stringUtils)
}