package com.fazq.rimayalert.features.profile.views.event

sealed class ProfileEvent {
    object OnChangePasswordClick : ProfileEvent()
    object OnAboutAppClick : ProfileEvent()
    object OnLogoutClick : ProfileEvent()
    object OnDismissDialog : ProfileEvent()
    object OnNotificationToggle : ProfileEvent()
    object OnCommunityClick : ProfileEvent()
    object OnOpenIncidentsMapClick : ProfileEvent()
}