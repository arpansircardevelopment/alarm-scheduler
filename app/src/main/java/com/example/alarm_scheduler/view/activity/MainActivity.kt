package com.example.alarm_scheduler.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.alarm_scheduler.R
import com.example.alarm_scheduler.databinding.ActivityMainBinding
import com.example.alarm_scheduler.model.repository.MainRepository
import com.example.alarm_scheduler.model.room.AlarmDao
import com.example.alarm_scheduler.model.room.AlarmDatabase
import com.example.alarm_scheduler.view.picker.DatePicker
import com.example.alarm_scheduler.viewmodel.MainViewModel
import com.example.alarm_scheduler.viewmodel.factory.MainViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var viewModel: MainViewModel? = null

    private var timePicker: MaterialTimePicker? = null
    private var datePicker: MaterialDatePicker<Long>? = null
    private val picker by lazy { DatePicker(this@MainActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeData()
        setObservers()
        setDatePicker()
        setListeners()
    }

    private fun setObservers() {
        viewModel?.getAlarms()?.observe(this@MainActivity) {
            // RecyclerView Code
        }
    }

    private fun initializeData() {
        val database: AlarmDatabase = AlarmDatabase.getInstance(this@MainActivity)
        val repository = MainRepository(database)

        viewModel = ViewModelProvider(
            owner = this@MainActivity, factory = MainViewModelFactory(
                repository = repository
            )
        )[MainViewModel::class.java]
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