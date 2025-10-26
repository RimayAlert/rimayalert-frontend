package com.fazq.rimayalert.features.auth.data.service

import androidx.annotation.WorkerThread
import com.fazq.rimayalert.core.functions.flowResponse
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.utils.StringUtils
import com.fazq.rimayalert.features.auth.data.api.RegisterUserApiClient
import com.fazq.rimayalert.features.auth.domain.model.RegisterUserModel
import com.fazq.rimayalert.features.common.interfaces.ErrorDao
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUserService @Inject constructor(
    private val api: RegisterUserApiClient,
    private val errorDao: ErrorDao,
    private val stringUtils: StringUtils
) {

    @WorkerThread
    suspend fun registerUser(user: RegisterUserModel): Flow<DataState<JsonObject>> = flowResponse(
        api.registerUser(user), "register user", errorDao, stringUtils
    )
}