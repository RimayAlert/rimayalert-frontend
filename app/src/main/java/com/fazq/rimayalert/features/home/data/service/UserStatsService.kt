package com.fazq.rimayalert.features.home.data.service

import androidx.annotation.WorkerThread
import com.fazq.rimayalert.core.functions.flowResponse
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.utils.StringUtils
import com.fazq.rimayalert.features.common.interfaces.ErrorDao
import com.fazq.rimayalert.features.home.data.api.UserStatsApiClient
import com.fazq.rimayalert.features.home.domain.model.UserStatsDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserStatsService @Inject constructor(
    private val api: UserStatsApiClient,
    private val stringUtils: StringUtils,
    private val errorDao: ErrorDao
) {
    @WorkerThread
    suspend fun getStats(): Flow<DataState<UserStatsDTO>> =
        flowResponse(api.getUserStats(), "getStats", errorDao, stringUtils)

}