package com.fazq.rimayalert.core.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.fazq.rimayalert.MainActivity
import com.fazq.rimayalert.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import androidx.core.content.edit

class FCMService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FCMService"
        private const val CHANNEL_ID = "rimay_alert_channel"
        private const val CHANNEL_NAME = "RimayAlert Notifications"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Nuevo token FCM: $token")

        saveTokenToPreferences(token)

        val data: Map<String, String> = mapOf(
            "type" to "push_token",
            "token" to token
        )
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d(TAG, "Mensaje recibido de: ${message.from}")

        message.notification?.let {
            showNotification(it.title, it.body, message.data)
        }

        if (message.data.isNotEmpty()) {
            Log.d(TAG, "Datos: ${message.data}")
            handleNotificationData(message.data)
        }
    }

    private fun handleNotificationData(data: Map<String, String>) {
        val incidentId = data["incident_id"]
        val incidentType = data["incident_type"]

        Log.d(TAG, "Incidente ID: $incidentId, Tipo: $incidentType")

    }

    private fun showNotification(title: String?, body: String?, data: Map<String, String>) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alertas de incidentes cercanos"
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            data["incident_id"]?.let { putExtra("incident_id", it) }
            data["incident_type"]?.let { putExtra("incident_type", it) }
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title ?: "RimayAlert")
            .setContentText(body ?: "Nueva alerta de incidente")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(1000, 1000, 1000))
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun saveTokenToPreferences(token: String) {
        val prefs = getSharedPreferences("fcm_prefs", Context.MODE_PRIVATE)
        prefs.edit { putString("fcm_token", token) }
        Log.d(TAG, "Token guardado localmente")
    }
}