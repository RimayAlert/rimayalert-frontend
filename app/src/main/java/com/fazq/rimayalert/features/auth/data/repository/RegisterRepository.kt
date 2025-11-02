package com.fazq.rimayalert.features.auth.data.repository

import com.fazq.rimayalert.core.di.IoDispatcher
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.auth.domain.entities.RegisterUserEntity
import com.fazq.rimayalert.features.auth.data.db.interfaces.RegisterDao
import com.fazq.rimayalert.features.auth.data.repository.interfaces.RegisterInterface
import com.fazq.rimayalert.features.auth.data.service.RegisterUserService
import com.fazq.rimayalert.features.auth.domain.model.RegisterUserModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val service: RegisterUserService,
    private val dao: RegisterDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : RegisterInterface {

    override suspend fun sendRegisterUserData(userData: RegisterUserModel): DataState<String> {
        if (userData.password != userData.confirmPassword) {
            return DataState.Error("Las contraseñas no coinciden", 400)
        }

        val response = service.registerUser(userData)
        var dataState: DataState<String> = DataState.error("")

        response
            .catch { dataState = DataState.error(it.message ?: "Error desconocido") }
            .flowOn(ioDispatcher)
            .collect { responseState ->
                dataState = when (responseState) {
                    is DataState.Success -> {
                        val message = responseState.data
                        try {
                            val entity = RegisterUserEntity(
                                username = userData.username,
                                dni = userData.dni,
                                firstName = userData.firstName,
                                lastName = userData.lastName,
                                displayName = userData.displayName,
                                phone = userData.phone,
                                email = userData.email
                            )
                            dao.insertUser(entity)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        DataState.success(userData.username)
                    }

                    is DataState.Error -> DataState.error(responseState.message, responseState.code)
                }
            }

        return dataState
    }

}