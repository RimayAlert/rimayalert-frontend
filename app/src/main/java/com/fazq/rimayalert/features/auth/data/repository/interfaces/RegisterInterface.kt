package com.fazq.rimayalert.features.auth.data.repository.interfaces

import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.auth.domain.model.RegisterModel

interface RegisterInterface {

    suspend fun sendRegisterData(): DataState<List<RegisterModel>>
}