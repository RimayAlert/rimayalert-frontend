package com.fazq.rimayalert.features.home.domain.model

data class UserStatsModel(
    val totalAlert: Int,
    val totalAlertResolved: Int,
    val totalAlertPending: Int,
    val topTypes: TopTypeModel?
)

data class TopTypeModel(
    val name: String,
    val count: Int,
    val percentage: Double
)

fun UserStatsDTO?.toDomain() = UserStatsModel(
    totalAlert = this?.totalAlert ?: 0,
    totalAlertResolved = this?.totalAlertResolved ?: 0,
    totalAlertPending = this?.totalAlertPending ?: 0,
    topTypes  = this?.topType?.toDomain()
)

fun TopTypeDTO.toDomain() = TopTypeModel(
    name = this.name,
    count = this.count,
    percentage = this.percentage
)

