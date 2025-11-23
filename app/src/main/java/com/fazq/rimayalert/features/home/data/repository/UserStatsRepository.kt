package com.fazq.rimayalert.features.home.data.repository

import android.util.Log
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.home.data.repository.interfaces.UserStatsnterface
import com.fazq.rimayalert.features.home.data.service.UserStatsService
import com.fazq.rimayalert.features.home.domain.model.UserStatsModel
import com.fazq.rimayalert.features.home.domain.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserStatsRepository @Inject constructor(
    private val apiService: UserStatsService
) : UserStatsnterface {

    override suspend fun getUserStats(): DataState<UserStatsModel> {
        val response = apiService.getStats()
        var dataState: DataState<UserStatsModel> = DataState.error("")
        response
            .catch { dataState = DataState.error(it.message ?: "Unknown Error") }
            .flowOn(Dispatchers.IO)
            .collect { responseState ->
                dataState = when (responseState) {
                    is DataState.Success -> {
                        Log.d("stats-raw", responseState.data.toString())
                        val stats = responseState.data.toDomain()
                        Log.d("stats-mapped", stats.toString())
                        DataState.success(stats)
                    }

                    is DataState.Error -> {
                        DataState.error(responseState.message)
                    }
                }
            }
        return dataState
    }
}