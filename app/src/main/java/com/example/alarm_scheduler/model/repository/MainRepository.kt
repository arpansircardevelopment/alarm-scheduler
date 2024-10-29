package com.example.alarm_scheduler.model.repository

import com.example.alarm_scheduler.model.alarm.AlarmScheduler
import com.example.alarm_scheduler.model.room.Alarm
import com.example.alarm_scheduler.model.room.AlarmDao
import com.example.alarm_scheduler.model.room.AlarmDatabase
import kotlinx.coroutines.flow.Flow

class MainRepository(
    database: AlarmDatabase,
    private val scheduler: AlarmScheduler
) {

    private val dao: AlarmDao = database.alarmDao()

    suspend fun insertAlarm(alarm: Alarm) = dao.insertAlarm(alarm)

    suspend fun deleteAlarm(timeInMillis: Long) = dao.deleteAlarm(timeInMillis)

    fun getAlarms(): Flow<List<Alarm>> = dao.getAllAlarms()

    fun getSpecificAlarm(timeInMillis: Long): Alarm? = dao.getAlarm(timeInMillis)

    fun scheduleAlarm(alarm: Alarm) = scheduler.scheduleAlarm(alarm)

}