package com.fazq.rimayalert.features.home.domain.usecase

import com.fazq.rimayalert.core.di.IoDispatcher
import com.fazq.rimayalert.core.functions.NetworkFunction
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.home.data.repository.CommunityRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CommunityUseCase @Inject constructor(
    private val repository: CommunityRepository,
    private val networkFunction: NetworkFunction,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun checkCommunityStatus(): DataState<Boolean> {
        return when (networkFunction.hasInternetConnection()) {
            true -> repository.checkCommunityStatus()
            false -> DataState.error("No hay conexión a Internet. Por favor, verifica tu conexión e intenta nuevamente.")
        }
    }

    suspend fun assignCommunity(latitude: Double, longitude: Double): DataState<Boolean> {
        return when (networkFunction.hasInternetConnection()) {
            true -> repository.assignCommunity(latitude, longitude)
            false -> DataState.error("No hay conexión a Internet. Por favor, verifica tu conexión e intenta nuevamente.")
        }
    }
}