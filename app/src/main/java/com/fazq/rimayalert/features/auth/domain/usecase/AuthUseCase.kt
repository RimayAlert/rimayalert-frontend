package com.fazq.rimayalert.features.auth.domain.usecase

import com.fazq.rimayalert.core.di.IoDispatcher
import com.fazq.rimayalert.core.functions.NetworkFunction
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.utils.TokenManager
import com.fazq.rimayalert.features.auth.data.mapper.AuthResponseDTO
import com.fazq.rimayalert.features.auth.data.repository.AuthRepository
import com.fazq.rimayalert.features.auth.domain.model.AuthModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val tokenManager: TokenManager,
    private val networkFunction: NetworkFunction,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
){
    suspend fun auth(authParam: AuthModel): DataState<AuthResponseDTO> {
        return withContext(ioDispatcher) {
            val response = when (networkFunction.hasInternetConnection()) {
                true -> repository.authenticationFromApi(authParam)
                false -> repository.authenticationFromDataBase(authParam.username)
            }
            if (response is DataState.Success) {
                val token = if ("Token" in response.data.token) response.data.token else "Token ${response.data.token}"
                response.data.let { tokenManager.saveToken(token, authParam.username) }
            }
            return@withContext response
        }
    }
}