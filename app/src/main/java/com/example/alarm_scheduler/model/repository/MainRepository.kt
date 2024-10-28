package com.example.alarm_scheduler.model.repository

import com.example.alarm_scheduler.model.room.Alarm
import com.example.alarm_scheduler.model.room.AlarmDao
import com.example.alarm_scheduler.model.room.AlarmDatabase
import kotlinx.coroutines.flow.Flow

class MainRepository(database: AlarmDatabase) {

    private val dao: AlarmDao = database.alarmDao()

    suspend fun insertAlarm(alarm: Alarm) = dao.insertAlarm(alarm)

    suspend fun deleteAlarm(alarm: Alarm) = dao.deleteAlarm(alarm)

    fun getAlarms(): Flow<List<Alarm>> = dao.getAllAlarms()

}