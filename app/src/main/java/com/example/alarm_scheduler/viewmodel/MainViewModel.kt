package com.example.alarm_scheduler.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.alarm_scheduler.model.repository.MainRepository
import com.example.alarm_scheduler.model.room.Alarm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val scope: CoroutineScope = viewModelScope

    fun insertAlarm(alarm: Alarm) {
        scope.launch {
            repository.insertAlarm(alarm)
        }
    }

    fun deleteAlarm(alarm: Alarm) {
        scope.launch {
            repository.deleteAlarm(alarm)
        }
    }

    fun getAlarms() = repository.getAlarms().asLiveData()

}