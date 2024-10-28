package com.example.alarm_scheduler.utils

import android.icu.util.Calendar

object DateTimeUtils {

    fun convertDateAndTimePickerTime(
        selectedDateInMillis: Long,
        selectedHour: Int,
        selectedMinute: Int
    ): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = selectedDateInMillis
        calendar.set(Calendar.HOUR, selectedHour)
        calendar.set(Calendar.MINUTE, selectedMinute)
        return calendar.timeInMillis
    }

    fun isTimeValid(enteredTimeInMilliseconds: Long): Boolean {
        val currentTimeInMillis = Calendar.getInstance().timeInMillis
        return currentTimeInMillis <= enteredTimeInMilliseconds
    }

}