package com.example.alarm_scheduler.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alarm_scheduler.databinding.ItemAlarmBinding
import com.example.alarm_scheduler.model.callback.AlarmItemListener
import com.example.alarm_scheduler.model.room.Alarm
import com.example.alarm_scheduler.utils.DateTimeUtils

class AlarmListingAdapter(
    private val alarmList: List<Alarm>,
    private val callback: AlarmItemListener
) : RecyclerView.Adapter<AlarmListingAdapter.AlarmListingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmListingViewHolder {
        val binding: ItemAlarmBinding = ItemAlarmBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return AlarmListingViewHolder(binding, callback)
    }

    override fun getItemCount(): Int = alarmList.size

    override fun onBindViewHolder(holder: AlarmListingViewHolder, position: Int) {
        holder.setData(alarmList[position])
    }

    class AlarmListingViewHolder(
        private val binding: ItemAlarmBinding,
        private val callback: AlarmItemListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setData(alarm: Alarm) {
            binding.tvAlarmDate.text = DateTimeUtils.getDateFromMilliseconds(alarm.dateTime)
            binding.tvAlarmTime.text = DateTimeUtils.getTimeFromMilliseconds(alarm.dateTime)
            binding.ivDeleteIcon.setOnClickListener {
                callback.deleteAlarm(alarm)
            }
        }
    }
}