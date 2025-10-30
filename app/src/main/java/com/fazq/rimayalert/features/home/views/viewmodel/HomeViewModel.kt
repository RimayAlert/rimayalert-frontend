package com.fazq.rimayalert.features.home.views.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.features.home.domain.usecase.GetAlertsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
//    private val getAlertsUseCase: GetAlertsUseCase,
//    private val getWeeklySummaryUseCase: GetWeeklySummaryUseCase
) : ViewModel() {

    private val _homeUiState = MutableStateFlow<BaseUiState>(BaseUiState.EmptyState)
    val homeUiState: StateFlow<BaseUiState> = _homeUiState.asStateFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _homeUiState.value = BaseUiState.LoadingState

//            when (val summaryResult = getWeeklySummaryUseCase.execute()) {
//                is DataState.Success -> {
//                    // Cargar alertas recientes
//                    when (val alertsResult = getAlertsUseCase.execute()) {
//                        is DataState.Success -> {
//                            _homeUiState.value = BaseUiState.SuccessState(
//                                mapOf(
//                                    "summary" to summaryResult.data,
//                                    "alerts" to alertsResult.data
//                                )
//                            )
//                        }
//                        is DataState.Error -> {
//                            _homeUiState.value = BaseUiState.ErrorState(alertsResult.message)
//                        }
//                    }
//                }
//                is DataState.Error -> {
//                    _homeUiState.value = BaseUiState.ErrorState(summaryResult.message)
//                }
//            }
        }
    }

    fun resetState() {
        _homeUiState.value = BaseUiState.EmptyState
    }
}