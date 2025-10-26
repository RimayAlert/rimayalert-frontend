package com.fazq.rimayalert.features.auth.data.repository.interfaces

import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.auth.domain.model.RegisterUserModel

interface RegisterInterface {

    suspend fun sendRegisterUserData(model: RegisterUserModel): DataState<String>
}