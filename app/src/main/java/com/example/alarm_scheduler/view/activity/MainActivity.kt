package com.example.alarm_scheduler.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm_scheduler.R
import com.example.alarm_scheduler.databinding.ActivityMainBinding
import com.example.alarm_scheduler.view.picker.DatePicker
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val picker by lazy { DatePicker(this@MainActivity) }

    private var datePicker: MaterialDatePicker<Long>? = null
    private var timePicker: MaterialTimePicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        setDatePicker()
        setListeners()
    }

    private fun setDatePicker() {
        datePicker = picker.getDatePicker()
    }

    private fun setListeners() {
        binding.btAddNewAlarm.setOnClickListener {
            datePicker?.show(supportFragmentManager, getString(R.string.date_picker_dialog_tag))
        }

        datePicker?.addOnPositiveButtonClickListener {
            triggerTimePicker(it)
        }
    }

    private fun triggerTimePicker(selectedDate: Long) {
        timePicker = picker.getTimePicker()
        timePicker?.show(supportFragmentManager, getString(R.string.time_picker_dialog_tag))
        timePicker?.addOnPositiveButtonClickListener {
            Timber.d("selectedDate: ${timePicker?.hour}:${timePicker?.minute}")
        }
    }
}