package com.fazq.rimayalert.core.functions

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.fazq.rimayalert.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress
import java.net.SocketTimeoutException
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkFunction @Inject constructor(
    private val appContext: Application
) {
    private val TAG = "NetworkFunction"

    suspend fun hasInternetConnection(): Boolean {
        val withContext = withContext(Dispatchers.IO) {
            Log.d(TAG, "=== Checking Network Connection ===")
            Log.d(TAG, "Base URL: ${BuildConfig.BASE_URL}")

            if (!isNetworkAvailable()) {
                Log.e(TAG, "❌ No network available")
                return@withContext false
            }

            Log.d(TAG, "✅ Network available, checking server...")

            val serverReachable = isServerReachable()

            if (serverReachable) {
                Log.d(TAG, "✅ Server is reachable")
            } else {
                Log.e(TAG, "❌ Server is NOT reachable")
            }

            return@withContext serverReachable
        }
        return withContext
    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            if (network == null) {
                Log.d(TAG, "No active network")
                return false
            }

            val capabilities = connectivityManager.getNetworkCapabilities(network)
            if (capabilities == null) {
                Log.d(TAG, "No network capabilities")
                return false
            }

            val hasTransport = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)

            val hasInternet = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

            Log.d(TAG, "Has transport: $hasTransport, Has internet: $hasInternet")

            hasTransport && hasInternet
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            val isConnected = networkInfo?.isConnected ?: false
            Log.d(TAG, "Network connected (legacy): $isConnected")
            isConnected
        }
    }


    private fun isServerReachable(): Boolean {
        return try {
            val url = URL(BuildConfig.BASE_URL)
            Log.d(TAG, "Attempting connection to: ${url.host}:${url.port}")

            val connection = url.openConnection() as HttpURLConnection
            connection.apply {
                requestMethod = "GET"
                connectTimeout = 5000
                readTimeout = 5000
                useCaches = false
                setRequestProperty("Connection", "close")
            }

            val responseCode = connection.responseCode
            connection.disconnect()

            val isSuccess = responseCode in 200..399
            Log.d(TAG, "Response code: $responseCode, Success: $isSuccess")

            isSuccess

        } catch (e: SocketTimeoutException) {
            Log.e(TAG, "Connection timeout: ${e.message}")
            false
        } catch (e: IOException) {
            Log.e(TAG, "IO Exception: ${e.message}")
            false
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error: ${e.message}")
            false
        }
    }


    fun isOnline(): Boolean {
        return try {
            val fullUrl = BuildConfig.BASE_URL
            val parts = fullUrl.replace("http://", "").replace("https://", "").split(":")
            val host = parts[0]
            val port = if (parts.size > 1) parts[1].toIntOrNull() ?: 80 else 80

            Log.d(TAG, "Socket check - Host: $host, Port: $port")

            val socket = Socket()
            val socketAddress: SocketAddress = InetSocketAddress(host, port)
            socket.connect(socketAddress, 3000) // 3 segundos timeout
            socket.close()

            Log.d(TAG, "Socket connection successful")
            true
        } catch (e: IOException) {
            Log.e(TAG, "Socket connection failed: ${e.message}")
            false
        } catch (e: Exception) {
            Log.e(TAG, "Socket unexpected error: ${e.message}")
            false
        }
    }
}