package com.fazq.rimayalert.features.auth.data.repository.interfaces

import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.auth.domain.model.AuthModel

interface AuthInterface {
    suspend fun authenticationFromApi(authParam: AuthModel): DataState<String>
    suspend fun authenticationFromDataBase(username: String): DataState<String>

}