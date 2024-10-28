package com.example.alarm_scheduler.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alarm_scheduler.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        setListeners()
    }

    private fun setListeners() {
        binding.btAddNewAlarm.setOnClickListener {

        }
    }
}