package com.fazq.rimayalert.features.auth.views.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.auth.domain.model.RegisterUserModel
import com.fazq.rimayalert.features.auth.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterUserViewModel @Inject constructor(
    private val useCase: RegisterUseCase
) : ViewModel() {

    private val _registerUserUiState = MutableStateFlow<BaseUiState>(BaseUiState.EmptyState)
    val registerUserUiState: StateFlow<BaseUiState> = _registerUserUiState.asStateFlow()

    fun registerUser(registerUser: RegisterUserModel) {
        viewModelScope.launch {
            _registerUserUiState.value = BaseUiState.LoadingState
            when (val responseState = useCase.registerUser(registerUser)) {
                is DataState.Success -> _registerUserUiState.value =
                    BaseUiState.SuccessState(responseState.data)

                is DataState.Error -> _registerUserUiState.value =
                    BaseUiState.ErrorState(responseState.message, responseState.code)
            }
        }
    }
}