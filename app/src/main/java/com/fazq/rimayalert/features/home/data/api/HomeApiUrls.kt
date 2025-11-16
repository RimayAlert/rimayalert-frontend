package com.fazq.rimayalert.features.home.data.api

object HomeApiUrls {

    private const val api = "api"

    private const val communities = "communities/${api}"

    private const val community = "${communities}/community"

    //    Comunity
    const val ASSIGN_COMMUNITY = "${community}/assign"

    //    STATS
    private const val stats = "stats/${api}"

    private const val user_stats = "${stats}/user-stats"

    const val GET_USER_STATS = "${user_stats}/list"


//    Incident

    private const val incident = "incidents/${api}"
    private const val alert = "${incident}/alert"
    const val GET_INCIDENT_INFO = "${alert}/list"

}