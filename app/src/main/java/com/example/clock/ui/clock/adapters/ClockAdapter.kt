package com.example.clock.ui.clock.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.clock.data.models.ClockData
import com.example.clock.databinding.ClockItemBinding

class ClockAdapter: ListAdapter<ClockData, ClockAdapter.ClockViewHolder>(MyDiffUtil) {

    inner class ClockViewHolder(private val binding: ClockItemBinding)
        :RecyclerView.ViewHolder(binding.root) {

            fun bind(position: Int) {

                val clockData = getItem(position)
                binding.tvTime.text = clockData.time
                binding.tvCity.text = clockData.city
                binding.tvTimeDifference.text = clockData.timeDifference

            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClockViewHolder {
        return ClockViewHolder(
            ClockItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: ClockViewHolder, position: Int) {
        holder.bind(position)
    }


    private object MyDiffUtil : DiffUtil.ItemCallback<ClockData>() {
        override fun areItemsTheSame(oldItem: ClockData, newItem: ClockData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ClockData, newItem: ClockData): Boolean {
            return oldItem.id == newItem.id && oldItem.time == newItem.time

        }

    }

    fun removeItem(item: ClockData){
        val currentList =  currentList.toMutableList()
        currentList.remove(item)
        submitList(currentList)

    }


}