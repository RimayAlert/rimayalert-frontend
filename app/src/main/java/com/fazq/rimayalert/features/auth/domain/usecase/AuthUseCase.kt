package com.fazq.rimayalert.features.auth.domain.usecase

import com.fazq.rimayalert.core.di.IoDispatcher
import com.fazq.rimayalert.core.functions.NetworkFunction
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.auth.data.repository.AuthRepository
import com.fazq.rimayalert.features.auth.domain.model.AuthModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val networkFunction: NetworkFunction,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun auth(authParam: AuthModel): DataState<String> {
        return withContext(ioDispatcher) {
            when (networkFunction.hasInternetConnection()) {
                true -> repository.authenticationFromApi(authParam)
                false -> repository.authenticationFromDataBase(authParam.username)
            }
        }
    }
}