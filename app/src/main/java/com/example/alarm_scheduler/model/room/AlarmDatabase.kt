package com.example.alarm_scheduler.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Alarm::class], version = 1)
abstract class AlarmDatabase : RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

    companion object {

        private var INSTANCE: AlarmDatabase? = null

        private const val DATABASE_NAME_SUFFIX: String = "-records.db"

        fun getInstance(context: Context): AlarmDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context.applicationContext).also {
                INSTANCE = it
            }
        }

        private fun buildDatabase(appContext: Context): AlarmDatabase {
            val databaseName = "${appContext.packageName}.$DATABASE_NAME_SUFFIX"
            return Room.databaseBuilder(appContext, AlarmDatabase::class.java, databaseName)
                .fallbackToDestructiveMigrationFrom()
                .build()
        }
    }
}