package com.example.alarm_scheduler.model.callback

import com.example.alarm_scheduler.model.room.Alarm

interface AlarmItemListener {
    fun deleteAlarm(alarm: Alarm)
}