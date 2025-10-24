package com.fazq.rimayalert.features.auth.views.viewmodel

import androidx.lifecycle.ViewModel
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.features.auth.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase : RegisterUseCase
): ViewModel(){

    private val _registerUiState = MutableStateFlow<BaseUiState>(BaseUiState.EmptyState)
    val registerUiState : StateFlow<BaseUiState> = _registerUiState.asStateFlow()



}