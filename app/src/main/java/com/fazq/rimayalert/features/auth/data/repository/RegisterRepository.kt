package com.fazq.rimayalert.features.auth.data.repository

import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.auth.data.db.interfaces.RegisterDao
import com.fazq.rimayalert.features.auth.data.repository.interfaces.RegisterInterface
import com.fazq.rimayalert.features.auth.domain.model.RegisterModel
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val service : RegisterService,
    private val dao : RegisterDao
) : RegisterInterface {

    override suspend fun sendRegisterData(): DataState<List<RegisterModel>> {
        TODO("Not yet implemented")
    }

}