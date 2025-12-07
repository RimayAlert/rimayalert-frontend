package com.fazq.rimayalert.features.profile.views.event

sealed class ProfileEvent {
    object OnLogoutClick : ProfileEvent()
    object OnDismissDialog : ProfileEvent()
    object OnNotificationToggle : ProfileEvent()
    object OnCommunityClick : ProfileEvent()
    object OnOpenIncidentsMapClick : ProfileEvent()
}