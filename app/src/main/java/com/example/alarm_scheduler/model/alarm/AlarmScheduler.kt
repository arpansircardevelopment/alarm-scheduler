package com.example.alarm_scheduler.model.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import com.example.alarm_scheduler.R
import com.example.alarm_scheduler.model.constants.Constants
import com.example.alarm_scheduler.model.room.Alarm
import com.example.alarm_scheduler.view.receiver.AlarmReceiver

class AlarmScheduler(private val context: Context) {

    private val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

    @SuppressLint("MissingPermission")
    fun scheduleAlarm(alarm: Alarm) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(Constants.ALARM_INTENT_TAG, context.getString(R.string.alarm_message))
            putExtra(Constants.ALARM_ID_INTENT_TAG, alarm.dateTime)
        }

        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.dateTime.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmTime = alarm.dateTime

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    alarmTime,
                    pendingIntent
                )
            }
        }
    }

    fun cancelAlarm(alarm: Alarm) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(Constants.ALARM_INTENT_TAG, context.getString(R.string.alarm_message))
            putExtra(Constants.ALARM_ID_INTENT_TAG, alarm.dateTime)
        }

        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.dateTime.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }
}