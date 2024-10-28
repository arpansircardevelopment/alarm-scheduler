package com.example.alarm_scheduler.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alarm_scheduler.model.repository.MainRepository
import com.example.alarm_scheduler.viewmodel.MainViewModel

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}