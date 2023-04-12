package com.example.clock.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.clock.data.ClockData

//class AlarmDiffCallback(
//    private val oldList: List<ClockData>,
//    private val newList: List<ClockData>
//): DiffUtil.Callback() {
//    override fun getOldListSize(): Int = oldList.size
//
//    override fun getNewListSize(): Int = newList.size
//
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        val oldTime = oldList[oldItemPosition]
//        val newTime = newList[newItemPosition]
//
//        return oldTime.id == newTime.id
//    }
//
//    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        val oldTime = oldList[oldItemPosition]
//        val newTime = newList[newItemPosition]
//
//        return oldTime == newTime
//    }
//
//}