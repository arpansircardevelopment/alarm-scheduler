package com.example.alarm_scheduler.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlarmDao {

    @Insert
    suspend fun insertAlarm(alarm: Alarm)

    @Query("SELECT * FROM alarm")
    suspend fun getAllAlarms(): List<Alarm>

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

}