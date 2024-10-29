package com.example.alarm_scheduler.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.alarm_scheduler.R
import com.example.alarm_scheduler.model.constants.Constants

object NotificationUtils {

    fun showNotification(context: Context?, notificationContent: String) {
        context?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val isPermissionProvided: Boolean = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED

                if (isPermissionProvided) {
                    val notification = getNotification(context, notificationContent)
                    notification?.let { notif ->
                        NotificationManagerCompat.from(context).notify(
                            Constants.ALARM_NOTIFICATION_ID,
                            notif
                        )
                    }
                }
            }
        }
    }

    private fun getNotification(context: Context?, notificationContent: String) = context?.let {
        NotificationCompat
            .Builder(context, Constants.ALARM_NOTIFICATION_CHANNEL_ID)
            .apply {
                setSmallIcon(R.drawable.ic_launcher_background)
                setContentTitle(context.getString(R.string.alarm_notification_title))
                setContentText(notificationContent)
                setPriority(NotificationCompat.PRIORITY_HIGH)
            }.build()
    }


    fun createNotificationChannel(context: Context?) = context?.let {
        val name: String = Constants.ALARM_APP_CHANNEL_NAME
        val description: String = Constants.ALARM_APP_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(
            Constants.ALARM_NOTIFICATION_CHANNEL_ID,
            name,
            importance
        )

        channel.description = description
        val notificationManager: NotificationManager =
            it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}