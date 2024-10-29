package com.example.alarm_scheduler.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarm_scheduler.R
import com.example.alarm_scheduler.databinding.ActivityMainBinding
import com.example.alarm_scheduler.model.alarm.AlarmScheduler
import com.example.alarm_scheduler.model.callback.AlarmItemListener
import com.example.alarm_scheduler.model.constants.Constants
import com.example.alarm_scheduler.model.repository.MainRepository
import com.example.alarm_scheduler.model.room.Alarm
import com.example.alarm_scheduler.model.room.AlarmDatabase
import com.example.alarm_scheduler.utils.DateTimeUtils
import com.example.alarm_scheduler.utils.NotificationUtils
import com.example.alarm_scheduler.view.picker.DatePicker
import com.example.alarm_scheduler.view.recyclerview.AlarmListingAdapter
import com.example.alarm_scheduler.viewmodel.MainViewModel
import com.example.alarm_scheduler.viewmodel.factory.MainViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker

class MainActivity : AppCompatActivity(), AlarmItemListener {

    private var viewModel: MainViewModel? = null
    private lateinit var binding: ActivityMainBinding

    private lateinit var timePicker: MaterialTimePicker
    private lateinit var datePicker: MaterialDatePicker<Long>
    private val picker by lazy { DatePicker(this@MainActivity) }

    private var selectedTimeInMillis: Long? = null
    private lateinit var alarm: Alarm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermissions()
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isPermissionGranted = ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (isPermissionGranted) {
                triggerInitialMethods()
            } else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    Constants.NOTIFICATION_REQUEST_CODE
                )
            }
        } else {
            triggerInitialMethods()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.NOTIFICATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                triggerInitialMethods()
            }
        }
    }

    private fun triggerInitialMethods() {
        initializeData()
        setObservers()
        setDateTimePicker()
        setListeners()
    }

    private fun setObservers() {
        viewModel?.getAlarms()?.observe(this@MainActivity) {
            val adapter = AlarmListingAdapter(it, this@MainActivity)
            binding.rvAlarms.apply {
                this.adapter = adapter
                this.layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }

        viewModel?.isAlarmPresentLiveData?.observe(this@MainActivity) { isPresent ->
            if (isPresent) {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.alarm_time_already_preset),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                scheduleAlarm(alarm)
            }
        }
    }

    private fun scheduleAlarm(alarm: Alarm) {
        viewModel?.insertAlarm(alarm)
        viewModel?.scheduleAlarm(alarm)
    }

    private fun initializeData() {
        val database: AlarmDatabase = AlarmDatabase.getInstance(this@MainActivity)
        val scheduler = AlarmScheduler(this@MainActivity)
        val repository = MainRepository(database, scheduler)

        viewModel = ViewModelProvider(
            owner = this@MainActivity, factory = MainViewModelFactory(
                repository = repository
            )
        )[MainViewModel::class.java]
        NotificationUtils.createNotificationChannel(this@MainActivity)
    }

    private fun setDateTimePicker() {
        datePicker = picker.getDatePicker()
        timePicker = picker.getTimePicker()
    }

    private fun setListeners() {
        binding.btAddNewAlarm.setOnClickListener {
            datePicker.show(supportFragmentManager, getString(R.string.date_picker_dialog_tag))
        }

        datePicker.addOnPositiveButtonClickListener {
            selectedTimeInMillis = it
            timePicker.show(supportFragmentManager, getString(R.string.time_picker_dialog_tag))
        }

        timePicker.addOnPositiveButtonClickListener {
            val selectedHour = timePicker.hour
            val selectedMinute = timePicker.minute
            validateAndStoreData(selectedTimeInMillis, selectedHour, selectedMinute)
        }
    }

    private fun validateAndStoreData(selectedDate: Long?, selectedHour: Int, selectedMinute: Int) {
        selectedDate?.let {

            val selectedTimeInMillis = DateTimeUtils.getAlarmTimeInMillis(
                selectedDate,
                selectedHour,
                selectedMinute
            )

            if (DateTimeUtils.isTimeValid(selectedTimeInMillis)) {
                alarm = Alarm(dateTime = selectedTimeInMillis)
                viewModel?.checkIfAlarmIsPresent(alarm)
            } else {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.invalid_date_time_message),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun deleteAlarm(alarm: Alarm) {
        viewModel?.deleteAlarm(alarm.dateTime)
    }
}