package com.fazq.rimayalert.core.utils

import android.app.Application
import com.fazq.rimayalert.R

class StringUtils(private val appContext: Application) {
    fun noNetworkErrorMessage() = appContext.getString(R.string.message_no_network_connected_str)
    fun somethingWentWrong() = appContext.getString(R.string.message_something_went_wrong_str)
//    fun googleMapsKey() = appContext.getString(R.string.google_maps_key)
    fun notFound() = appContext.getString(R.string.message_not_found_str)
}
