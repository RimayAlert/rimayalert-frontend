package com.fazq.rimayalert.features.home.data.api

object HomeApiUrls {

    private const val api = "api"

    private const val communities = "communities/${api}"

    private const val community = "${communities}/community"

//    Comunity
    const val ASSIGN_COMMUNITY = "${community}/assign"

//    Incident

    private const val incident = "incidents/${api}"
    private const val alert = "${incident}/alert"
    const val GET_INCIDENT_INFO = "${alert}/list"

}