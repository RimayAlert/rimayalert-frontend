package com.fazq.rimayalert.features.auth.domain.usecase

import com.fazq.rimayalert.core.singleton.IoDispatcher
import com.fazq.rimayalert.features.auth.data.repository.RegisterRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RegisterUseCase@Inject constructor(
    private val repository: RegisterRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
){

}