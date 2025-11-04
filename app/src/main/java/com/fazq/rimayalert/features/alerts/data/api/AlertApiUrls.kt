package com.fazq.rimayalert.features.alerts.data.api

object AlertApiUrls {
    private const val api = "api"

    private const val incidents = "incidents/${api}"

    private const val alerts = "${incidents}/alert"


//    alerts

    const val CREATE_ALERT = "${alerts}/create"
}