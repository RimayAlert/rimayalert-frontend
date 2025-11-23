package com.fazq.rimayalert.features.home.domain.model

import com.google.gson.annotations.SerializedName

data class UserStatsDTO(
    @SerializedName("total_alerts")
    val totalAlert: Int = 0,
    @SerializedName("total_alerts_resolved")
    val totalAlertResolved: Int = 0,
    @SerializedName("total_alerts_pending")
    val totalAlertPending: Int = 0,
    @SerializedName("top_type")
    val topType: TopTypeDTO? = null
)

data class TopTypeDTO(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("percentage")
    val percentage: Double = 0.0,
)