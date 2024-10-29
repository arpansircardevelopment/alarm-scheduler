package com.example.alarm_scheduler.utils

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.alarm_scheduler.R
import com.example.alarm_scheduler.model.constants.Constants
import com.example.alarm_scheduler.view.receiver.DismissAlarmReceiver

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
                    notification?.let { nonNullNotification ->
                        NotificationManagerCompat.from(context).notify(
                            Constants.ALARM_NOTIFICATION_ID,
                            nonNullNotification
                        )
                    }
                }
            }
        }
    }

    private fun getNotification(
        context: Context?,
        notificationContent: String
    ): Notification? = context?.let {
        val builder = NotificationCompat
            .Builder(context, Constants.ALARM_NOTIFICATION_CHANNEL_ID)
            .apply {
                setSmallIcon(R.drawable.ic_launcher_background)
                setContentTitle(context.getString(R.string.alarm_notification_title))
                setContentText(notificationContent)
                setPriority(NotificationCompat.PRIORITY_DEFAULT)
            }

        val dismissIntent = Intent(context, DismissAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            it,
            0,
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val dismissAction = NotificationCompat.Action(
            R.drawable.baseline_close_24,
            context.getString(R.string.dismiss),
            pendingIntent
        )

        builder.addAction(dismissAction)
        return@let builder.build()
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