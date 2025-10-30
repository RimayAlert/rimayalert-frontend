package com.fazq.rimayalert.features.auth.data.api

object AuthApiUrls {

    private const val api = "api"

    private const val auth = "api-auth"

    const val AUTH_URL = "${auth}-user"

    const val REFRESH_TOKEN_URL = "${auth}-token/refresh"

//    REGISTER

    const val register = "${api}/register"

    const val REGISTER_USER = "${register}/user"

}