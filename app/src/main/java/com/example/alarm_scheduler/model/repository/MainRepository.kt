package com.example.alarm_scheduler.model.repository

import com.example.alarm_scheduler.model.room.Alarm
import com.example.alarm_scheduler.model.room.AlarmDao
import com.example.alarm_scheduler.model.room.AlarmDatabase
import com.example.alarm_scheduler.utils.DateTimeUtils
import kotlinx.coroutines.flow.Flow

class MainRepository(database: AlarmDatabase) {

    private val dao: AlarmDao = database.alarmDao()
    private val utils: DateTimeUtils = DateTimeUtils

    suspend fun insertAlarm(alarm: Alarm) = dao.insertAlarm(alarm)

    suspend fun deleteAlarm(alarm: Alarm) = dao.deleteAlarm(alarm)

    fun getAlarms(): Flow<List<Alarm>> = dao.getAllAlarms()

    fun getAlarmTimeInMillis(dateInMillis: Long, hour: Int, minute: Int) =
        utils.getAlarmTimeInMillis(dateInMillis, hour, minute)

    fun checkIsTimeValid(timeInMillis: Long) = utils.isTimeValid(timeInMillis)

}