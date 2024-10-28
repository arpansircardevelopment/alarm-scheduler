package com.example.alarm_scheduler.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

object DateTimeUtils {

    fun getAlarmTimeInMillis(
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

    fun getDateFromMilliseconds(timeInMillis: Long): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEEE, dd MMM", Locale.getDefault())
        calendar.timeInMillis = timeInMillis
        return dateFormat.format(calendar.time)
    }

    fun getTimeFromMilliseconds(timeInMillis: Long): String {
        val calendar = Calendar.getInstance()
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        calendar.timeInMillis = timeInMillis
        return timeFormat.format(calendar.time)
    }

}