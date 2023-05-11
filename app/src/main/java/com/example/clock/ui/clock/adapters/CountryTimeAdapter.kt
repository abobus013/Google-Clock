package com.example.clock.ui.clock.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.clock.data.models.CountryTime
import com.example.clock.databinding.ListItemCountryTimeBinding
import com.example.clock.ui.clock.add_time.DBHelper

class CountryTimeAdapter(private val countries: List<Pair<String, String>>): ListAdapter<CountryTime,CountryTimeAdapter.ViewHolder>(MyDiffUtil) {

    inner class ViewHolder(private val binding: ListItemCountryTimeBinding)
        :RecyclerView.ViewHolder(binding.root) {

            fun bind(position: Int) {
                val searchCountyData = getItem(position)
                binding.textCountryName.text = searchCountyData.name
                binding.textCountryTime.text = searchCountyData.time
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemCountryTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }


    private object MyDiffUtil : DiffUtil.ItemCallback<CountryTime>() {
        override fun areItemsTheSame(oldItem: CountryTime, newItem: CountryTime): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CountryTime, newItem: CountryTime): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name

        }

    }

}
