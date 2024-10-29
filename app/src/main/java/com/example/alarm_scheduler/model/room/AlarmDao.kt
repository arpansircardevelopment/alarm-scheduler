package com.example.alarm_scheduler.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Insert
    suspend fun insertAlarm(alarm: Alarm)

    @Query("SELECT * FROM alarm")
    fun getAllAlarms(): Flow<List<Alarm>>

    @Query("SELECT * FROM alarm WHERE alarm_date_time = :timeInMillis")
    fun getAlarm(timeInMillis: Long): Alarm?

    @Query("DELETE FROM alarm WHERE alarm_date_time = :timeInMillis")
    suspend fun deleteAlarm(timeInMillis: Long)

}