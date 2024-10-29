package com.example.alarm_scheduler.view.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.example.alarm_scheduler.model.constants.Constants
import com.example.alarm_scheduler.utils.AudioUtils

class DismissAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            NotificationManagerCompat.from(context).cancel(Constants.ALARM_NOTIFICATION_ID)
            AudioUtils.stopRingtone(context)
        }
    }
}