package com.fazq.rimayalert.core.functions

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import com.fazq.rimayalert.BuildConfig.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.MalformedURLException
import java.net.Socket
import java.net.SocketAddress
import java.net.SocketTimeoutException
import java.net.URL


class NetworkFunction(private val appContext: Application) {
    suspend fun hasInternetConnection(): Boolean {
        val cm = appContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        val hasInternet = if (netInfo != null && netInfo.isConnected) {
            withContext(Dispatchers.IO) {
                try {
                    val policy = ThreadPolicy.Builder().permitAll().build()
                    StrictMode.setThreadPolicy(policy)
                    val url = URL(BASE_URL)
                    Log.d("NetworkFunction", "Checking internet connection to $BASE_URL")
                    val urlConnection = url.openConnection() as HttpURLConnection
                    urlConnection.connectTimeout = 7000
                    urlConnection.readTimeout = 7000
                    urlConnection.connect()
                    urlConnection.responseCode == 200
                } catch (e1: MalformedURLException) {
                    false
                } catch (e: SocketTimeoutException) {
                    false
                } catch (e: IOException) {
                    false
                }
            }
        } else false
        Log.d("NetworkFunction", "hasInternetConnection: $hasInternet")
        return hasInternet
    }

    fun isOnline(): Boolean {
        val ip: String = BASE_URL.split(":")[1].replace("/", "")
        val port = BASE_URL.split(":")[2].replace("/", "")
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockaddr: SocketAddress = InetSocketAddress(ip, port.toInt())
            sock.connect(sockaddr, timeoutMs)
            sock.close()
            true
        } catch (e: IOException) {
            false
        }
    }
}

fun hasInternetConnection(context: Context): Boolean {
    val cm = context
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    val hasInternet = if (netInfo != null && netInfo.isConnected) {
        try {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val url = URL(BASE_URL)
            Log.d("NetworkFunction", "Checking internet connection to $BASE_URL")
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connectTimeout = 7000
            urlConnection.readTimeout = 7000
            urlConnection.connect()
            urlConnection.responseCode == 200
        } catch (e1: MalformedURLException) {
            e1.printStackTrace()
            false
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
            false
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    } else false
    Log.d("NetworkFunction", "hasInternetConnection: $hasInternet")
    return hasInternet
}
