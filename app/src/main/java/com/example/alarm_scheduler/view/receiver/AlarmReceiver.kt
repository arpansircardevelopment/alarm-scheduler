package com.example.alarm_scheduler.view.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.alarm_scheduler.model.constants.Constants
import com.example.alarm_scheduler.model.room.AlarmDatabase
import com.example.alarm_scheduler.utils.NotificationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message: String = intent?.getStringExtra(Constants.ALARM_INTENT_TAG) ?: return
        val alarmId: Long = intent.getLongExtra(Constants.ALARM_ID_INTENT_TAG, -1) ?: return
        NotificationUtils.showNotification(context, message)

        CoroutineScope(Dispatchers.IO).launch {
            context?.let {
                val database = AlarmDatabase.getInstance(context)
                val dao = database.alarmDao()
                dao.deleteAlarm(alarmId)
            }
        }
    }
}