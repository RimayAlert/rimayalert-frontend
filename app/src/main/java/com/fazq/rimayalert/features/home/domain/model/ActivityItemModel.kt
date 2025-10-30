package com.fazq.rimayalert.features.home.domain.model

data class ActivityItemModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val time: String,
    val status: AlertStatus
)


enum class AlertStatus {
    EMERGENCY, WARNING, SUCCESS
}