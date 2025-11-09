package com.fazq.rimayalert.features.home.data.repository.interfaces

import com.fazq.rimayalert.core.states.DataState

interface CommunityInterface {

    suspend fun checkCommunityStatus(): DataState<Boolean>

    suspend fun assignCommunity(latitude: Double, longitude: Double): DataState<Boolean>
}