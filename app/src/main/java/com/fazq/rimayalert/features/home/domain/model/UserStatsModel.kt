package com.fazq.rimayalert.features.home.domain.model

data class UserStatsModel(
    val totalAlert: Int,
    val totalAlertResolved: Int,
    val totalAlertPending: Int
)

fun UserStatsDTO?.toDomain() = UserStatsModel(
    totalAlert = this?.totalAlert ?: 0,
    totalAlertResolved = this?.totalAlertResolved ?: 0,
    totalAlertPending = this?.totalAlertPending ?: 0
)


