package com.fazq.rimayalert.features.home.views.event

sealed class HomeEvent {
    data object ValidateOrAssignCommunity : HomeEvent()
}