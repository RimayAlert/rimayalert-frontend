package com.fazq.rimayalert.features.auth.data.service

import androidx.annotation.WorkerThread
import com.fazq.rimayalert.core.functions.flowResponse
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.utils.StringUtils
import com.fazq.rimayalert.features.auth.data.api.AuthApiClient
import com.fazq.rimayalert.features.auth.data.mapper.AuthRequestDTO
import com.fazq.rimayalert.features.auth.data.mapper.AuthResponseDTO
import com.fazq.rimayalert.features.common.interfaces.ErrorDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthService @Inject constructor(
    private val apiAuth: AuthApiClient,
    private val stringUtils: StringUtils,
    private val errorDao: ErrorDao
){
    @WorkerThread
    suspend fun authentication(data: AuthRequestDTO): Flow<DataState<AuthResponseDTO>> =
        flowResponse(apiAuth.authentication(data), "authentication", errorDao, stringUtils)

}
