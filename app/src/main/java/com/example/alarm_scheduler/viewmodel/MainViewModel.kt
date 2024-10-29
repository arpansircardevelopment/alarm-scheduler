package com.example.alarm_scheduler.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.alarm_scheduler.model.repository.MainRepository
import com.example.alarm_scheduler.model.room.Alarm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val scope: CoroutineScope = viewModelScope

    private val _isAlarmPresentLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isAlarmPresentLiveData: LiveData<Boolean> = _isAlarmPresentLiveData

    fun insertAlarm(alarm: Alarm) {
        scope.launch(Dispatchers.IO) {
            repository.insertAlarm(alarm)
        }
    }

    fun deleteAlarm(timeInMillis: Long) {
        scope.launch(Dispatchers.IO) {
            repository.deleteAlarm(timeInMillis)
        }
    }

    fun getAlarms() = repository.getAlarms().asLiveData()

    fun checkIfAlarmIsPresent(alarm: Alarm) {
        scope.launch(Dispatchers.IO) {
            val insertedAlarm = repository.getSpecificAlarm(alarm.dateTime)
            if (insertedAlarm == null) {
                _isAlarmPresentLiveData.postValue(false)
            } else {
                _isAlarmPresentLiveData.postValue(true)
            }
        }
    }

    fun scheduleAlarm(alarm: Alarm) {
        repository.scheduleAlarm(alarm)
    }
}