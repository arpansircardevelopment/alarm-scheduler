package com.example.alarm_scheduler.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm_scheduler.R
import com.example.alarm_scheduler.databinding.ActivityMainBinding
import com.example.alarm_scheduler.view.picker.DateTimePicker
import com.google.android.material.datepicker.MaterialDatePicker

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var datePicker: MaterialDatePicker<Long>? = null
    private val picker by lazy { DateTimePicker(this@MainActivity) }

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
            datePicker?.show(
                supportFragmentManager,
                getString(R.string.date_picker_dialog_tag)
            )
        }

        datePicker?.addOnPositiveButtonClickListener {
        }
    }
}