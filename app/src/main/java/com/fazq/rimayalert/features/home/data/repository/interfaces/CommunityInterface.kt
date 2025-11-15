package com.fazq.rimayalert.features.home.data.repository.interfaces

import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.home.domain.model.CommunityValidationResponseModel

interface CommunityInterface {
    suspend fun validateOrAssignCommunity(latitude: Double, longitude: Double): DataState<CommunityValidationResponseModel>
}