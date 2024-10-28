package com.example.alarm_scheduler.view.picker

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.alarm_scheduler.R
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker

class DateTimePicker(
    private val manager: FragmentManager,
    private val context: Context
) {

    fun showDatePicker() {
        val picker = MaterialDatePicker.Builder
            .datePicker()
            .apply {
                setTitleText(context.getString(R.string.select_date))
                setCalendarConstraints(getDateConstraints())
                setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            }.build()

        picker.show(
            this@DateTimePicker.manager,
            context.getString(R.string.date_picker_dialog_tag)
        )
    }

    private fun getDateConstraints(): CalendarConstraints = CalendarConstraints
        .Builder()
        .setValidator(
            DateValidatorPointForward.now()
        )
        .build()

}