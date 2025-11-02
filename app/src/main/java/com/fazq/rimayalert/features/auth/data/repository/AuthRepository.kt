package com.fazq.rimayalert.features.auth.data.repository

import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.core.utils.StringUtils
import com.fazq.rimayalert.core.utils.TokenManager
import com.fazq.rimayalert.features.auth.data.db.interfaces.UserDao
import com.fazq.rimayalert.features.auth.data.mapper.toModel
import com.fazq.rimayalert.features.auth.data.repository.interfaces.AuthInterface
import com.fazq.rimayalert.features.auth.data.service.AuthService
import com.fazq.rimayalert.features.auth.domain.entities.UserEntity
import com.fazq.rimayalert.features.auth.domain.entities.toEntity
import com.fazq.rimayalert.features.auth.domain.model.AuthModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthService,
    private val userDao: UserDao,
    private val tokenManager: TokenManager,
    private val stringUtils: StringUtils,
    ): AuthInterface{


        override suspend fun authenticationFromApi(authParam: AuthModel): DataState<String> {
            val response = api.authentication(authParam.toModel())
            var dataState: DataState<String> = DataState.error("")
            response
                .catch { dataState = DataState.error(it.message ?: "Error") }
                .flowOn(Dispatchers.IO)
                .collect { responseState ->
                    dataState = when (responseState) {
                        is DataState.Success -> {
                            val userDto = responseState.data.user
                            val userEntity = userDto.toEntity(responseState.data.token)
                            userDao.insertUser(userEntity)
                            DataState.success(responseState.data.token)
                        }
                        is DataState.Error -> {
                            val data = authenticationFromDataBase(authParam.username)
                            if (data is DataState.Success) {
                                DataState.success(data.data)
                            } else {
                                DataState.error(responseState.message)
                            }
                        }
                    }

                }
            return dataState
        }


    override suspend fun authenticationFromDataBase(username: String): DataState<String> {
        val dataState: DataState<String> = when (checkUser(username)) {
            true -> DataState.success(getToken(username))
            false -> DataState.error(stringUtils.noNetworkErrorMessage())
        }
        return dataState
    }

    private suspend fun checkUser(username: String): Boolean {
        return userDao.updateActiveUser(username) > 0
    }

    private suspend fun getToken(username: String): String {
        val querySet = userDao.getUser(username)
        if (querySet.isEmpty()) return ""
        val user: UserEntity = querySet.first()
        return user.token ?: ""
    }


}