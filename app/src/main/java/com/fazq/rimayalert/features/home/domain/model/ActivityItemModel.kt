package com.fazq.rimayalert.features.home.domain.model

data class ActivityItemModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val time: String,
    val status: ActivityStatus,
)


enum class ActivityStatus {
    ACTIVE,
    IN_PROGRESS,
    RESOLVED
}