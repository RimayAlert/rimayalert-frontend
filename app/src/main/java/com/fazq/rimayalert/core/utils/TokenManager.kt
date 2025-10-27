package com.fazq.rimayalert.core.utils

import com.fazq.rimayalert.core.constants.AppStrings.IS_LOGGED_IN
import com.fazq.rimayalert.core.constants.AppStrings.USER_REFRESH_TOKEN
import com.fazq.rimayalert.core.constants.AppStrings.USER_TOKEN
import javax.inject.Inject

class TokenManager @Inject constructor(
    private val sharedPref: SharedPref,
) {
    fun saveToken(token: String, username: String) {
        sharedPref.save(USER_TOKEN, token)
        sharedPref.save(USER_REFRESH_TOKEN, username)
        sharedPref.save(IS_LOGGED_IN, "true")
    }

    fun saveRefreshToken(token: String) {
        sharedPref.save(USER_TOKEN, token)
    }

    fun getToken(): String = sharedPref.read(USER_TOKEN)

    fun getRefreshToken(): String = sharedPref.read(USER_REFRESH_TOKEN)

    fun clearToken() {
        sharedPref.remove(USER_TOKEN)
        sharedPref.remove(USER_REFRESH_TOKEN)
        sharedPref.remove(IS_LOGGED_IN)
    }
}