package com.fazq.rimayalert.features.home.data.repository.interfaces

import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.home.domain.model.UserStatsModel

interface UserStatsnterface {
    suspend fun getUserStats(): DataState<UserStatsModel>
}