package com.example.alarm_scheduler.application

import android.app.Application
import com.example.alarm_scheduler.BuildConfig
import timber.log.Timber

class AlarmSchedulerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}