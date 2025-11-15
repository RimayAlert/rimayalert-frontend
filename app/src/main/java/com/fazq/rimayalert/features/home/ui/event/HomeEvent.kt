package com.fazq.rimayalert.features.home.ui.event

sealed class HomeEvent {
    data object ValidateOrAssignCommunity : HomeEvent()
}