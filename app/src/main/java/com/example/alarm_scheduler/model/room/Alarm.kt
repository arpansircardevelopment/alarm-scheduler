package com.example.alarm_scheduler.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm")
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "alarm_id")
    val id: Int? = null,

    @ColumnInfo(name = "alarm_date_time")
    val dateTime: Long,
)