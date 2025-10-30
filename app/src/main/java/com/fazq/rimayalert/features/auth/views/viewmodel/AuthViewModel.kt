package com.fazq.rimayalert.features.auth.views.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.core.states.DataState
import com.fazq.rimayalert.features.auth.domain.model.AuthModel
import com.fazq.rimayalert.features.auth.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,

    ) : ViewModel(){

    private val _authUiState = MutableStateFlow<BaseUiState>(BaseUiState.EmptyState)
    val authUiState: StateFlow<BaseUiState> = _authUiState.asStateFlow()



    fun auth(authParam: AuthModel) {
        viewModelScope.launch {
            _authUiState.value = BaseUiState.LoadingState
            when (val responseState = authUseCase.auth(authParam)) {
                is DataState.Success -> _authUiState.value =
                    BaseUiState.SuccessState(responseState.data)

                is DataState.Error -> _authUiState.value =
                    BaseUiState.ErrorState(responseState.message)
            }

        }
    }

    fun resetState() {
        _authUiState.value = BaseUiState.EmptyState
    }

}