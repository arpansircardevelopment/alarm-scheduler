package com.example.alarm_scheduler.view.picker

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.alarm_scheduler.R
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker

class DateTimePicker(private val context: Context) {

    fun getDatePicker() = MaterialDatePicker.Builder
        .datePicker()
        .apply {
            setTitleText(context.getString(R.string.select_date))
            setCalendarConstraints(getDateConstraints())
            setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        }.build()

    private fun getDateConstraints(): CalendarConstraints = CalendarConstraints
        .Builder()
        .setValidator(
            DateValidatorPointForward.now()
        )
        .build()

}