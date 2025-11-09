package com.fazq.rimayalert.features.home.data.api

object HomeApiUrls {

    private const val api = "api"

    private const val communities = "communities/${api}"

    private const val community = "${communities}/community"

//    Comunity

    const val CHECK_COMMUNITY_STATUS = "${community}/check"
    const val ASSIGN_COMMUNITY = "${community}/assign"
}