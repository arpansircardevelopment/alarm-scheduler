package com.example.alarm_scheduler.utils

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import com.example.alarm_scheduler.model.alarm.AlarmScheduler

object AudioUtils {

    private lateinit var ringtoneManager: Ringtone

    private fun initializeRingtoneManager(context: Context) {
        val alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtoneManager = RingtoneManager.getRingtone(context, alarmUri)
    }

    fun playRingtone(context: Context?) {
        context?.let {
            if (AudioUtils::ringtoneManager.isInitialized) {
                ringtoneManager.play()
            } else {
                initializeRingtoneManager(it)
                playRingtone(context)
            }
        }
    }

    fun stopRingtone(context: Context?) {
        if (ringtoneManager.isPlaying) {
            ringtoneManager.stop()
        }
    }
}