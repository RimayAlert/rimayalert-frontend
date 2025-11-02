package com.fazq.rimayalert.features.auth.domain.usecase

import com.fazq.rimayalert.core.di.IoDispatcher
import com.fazq.rimayalert.core.functions.NetworkFunction
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.utils.StringUtils
import com.fazq.rimayalert.features.auth.data.repository.RegisterRepository
import com.fazq.rimayalert.features.auth.domain.model.RegisterUserModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository,
    private val networkFunction: NetworkFunction,
    private val stringUtils: StringUtils,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun registerUser(model: RegisterUserModel): DataState<String> =
        withContext(ioDispatcher) {
            if (!networkFunction.hasInternetConnection()) {
                return@withContext DataState.error(stringUtils.noNetworkErrorMessage())
            }
            return@withContext when (val response = repository.sendRegisterUserData(model)) {
                is DataState.Success -> response
                is DataState.Error -> response
            }
        }
}