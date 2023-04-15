package com.example.clock.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clock.data.ClockData
import com.example.clock.data.ClocksDao
import com.example.clock.databinding.ItemLayoutBinding

class ClocksAdapter(
    private var onUpdateClickListener: ((ClockData) -> Unit)? = null,
) : RecyclerView.Adapter<ClocksAdapter.ClockViewHolder>() {

    private var onDayClickListener: ((ClockData) -> Unit)? = null

    private var onDeleteClickListener: ((ClockData) -> Unit)? = null

    private var onDatPickerClickListener: ((ItemLayoutBinding) -> Unit)? = null

    private var onDescriptionClickListener: ((ItemLayoutBinding) -> Unit)? = null



    fun setOnDescriptionClickListener(block: (ItemLayoutBinding) -> Unit) {
        onDescriptionClickListener = block
    }

    fun setOnDeleteClickListener(block: (ClockData) -> Unit) {
        onDeleteClickListener = block
    }

    fun setOnUpdateClickListener(block: (ClockData) -> Unit) {
        onUpdateClickListener = block
    }


    fun setOnDataPickerClickListener(block: (ItemLayoutBinding) -> Unit) {
        onDatPickerClickListener = block
    }




    fun setOnDayClickListener(block: (ClockData) -> Unit) {
        onDayClickListener = block
    }




    inner class ClockViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val clockData = models[adapterPosition]
            binding.tvTime.text = clockData.time

            daysToTextView(clockData,binding)





            binding.tvDescription.setOnClickListener {
                onDescriptionClickListener?.invoke(binding)

            }


            binding.root.setOnClickListener {
                onDeleteClickListener?.invoke(clockData)
            }

            binding.ivArrow.setOnClickListener {
                if (binding.expandedL.isExpanded) {
                    binding.expandedL.collapse()
                    binding.secondExpandedL.collapse()


                } else {
                    binding.expandedL.toggle()
                    binding.secondExpandedL.toggle()
                }
            }

            binding.root.setOnClickListener {
                if (binding.expandedL.isExpanded) {
                    binding.expandedL.collapse()
                    binding.secondExpandedL.collapse()
                } else {
                    binding.expandedL.toggle()
                    binding.secondExpandedL.toggle()
                }
            }

            binding.tvDelete.setOnClickListener {
                models.remove(clockData)
                notifyItemRemoved(adapterPosition)
                onDeleteClickListener?.invoke(clockData)
            }

            binding.tvTime.setOnClickListener {
                onUpdateClickListener?.invoke(clockData)
            }

            binding.tvAddDataAlarm.setOnClickListener {
                onDatPickerClickListener?.invoke(binding)
            }



            binding.tvMonday.isSelected = clockData.monday
            binding.tvTuesday.isSelected = clockData.tuesday
            binding.tvWednesday.isSelected = clockData.wednesday
            binding.tvThursday.isSelected = clockData.thursday
            binding.tvFriday.isSelected = clockData.friday
            binding.tvSaturday.isSelected = clockData.saturday
            binding.tvSunday.isSelected = clockData.sunday


            binding.tvMonday.setOnClickListener {
                binding.tvMonday.isSelected = clockData.monday.not()
                clockData.monday = clockData.monday.not()
                onDayClickListener?.invoke(clockData)
                daysToTextView(clockData,binding)

            }



            binding.tvTuesday.setOnClickListener {
                binding.tvTuesday.isSelected = clockData.tuesday.not()
                clockData.tuesday = clockData.tuesday.not()
                onDayClickListener?.invoke(clockData)
                daysToTextView(clockData,binding)
            }

            binding.tvWednesday.setOnClickListener {
                binding.tvWednesday.isSelected = clockData.wednesday.not()
                clockData.wednesday = clockData.wednesday.not()
                onDayClickListener?.invoke(clockData)
                daysToTextView(clockData,binding)
            }

            binding.tvThursday.setOnClickListener {
                binding.tvThursday.isSelected = clockData.thursday.not()
                clockData.thursday = clockData.thursday.not()
                onDayClickListener?.invoke(clockData)
                daysToTextView(clockData,binding)
            }

            binding.tvFriday.setOnClickListener {
                binding.tvFriday.isSelected = clockData.friday.not()
                clockData.friday = clockData.friday.not()
                onDayClickListener?.invoke(clockData)
                daysToTextView(clockData,binding)
            }

            binding.tvSaturday.setOnClickListener {
                binding.tvSaturday.isSelected = clockData.saturday.not()
                clockData.saturday = clockData.saturday.not()
                onDayClickListener?.invoke(clockData)
                daysToTextView(clockData,binding)
            }


            binding.tvSunday.setOnClickListener {
                binding.tvSunday.isSelected = clockData.sunday.not()
                clockData.sunday = clockData.sunday.not()
                onDayClickListener?.invoke(clockData)
                daysToTextView(clockData,binding)
            }


        }



    }

    var models = mutableListOf<ClockData>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClockViewHolder {
        return ClockViewHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }


    override fun getItemCount() = models.size

    override fun onBindViewHolder(holder: ClockViewHolder, position: Int) {
        holder.bind()
    }

    @SuppressLint("SetTextI18n")
    fun daysToTextView(alarmData: ClockData, binding: ItemLayoutBinding) {
        val isActivatedDaysList = booleanArrayOf(alarmData.monday, alarmData.tuesday,
            alarmData.wednesday, alarmData.thursday, alarmData.friday,
            alarmData.saturday, alarmData.sunday
        )
        val weekDays = listOf("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье")
        val shortWeekDays = listOf("пн", "вт", "ср", "чт", "пт", "сб", "вс")
        var oneDay = ""
        val selectedDays = mutableListOf<String>()
        for (i in isActivatedDaysList.indices) {
            if (isActivatedDaysList[i]) {
                selectedDays.add(shortWeekDays[i])
                oneDay = weekDays[i]
            }
        }
        if (selectedDays.size == 7) {
            binding.tvNotSchedul.text = "Каждый день"
        } else if (selectedDays.size == 1) {
            binding.tvNotSchedul.text = oneDay
        } else if (selectedDays.isEmpty()) {
            binding.tvNotSchedul.text = "Не установлен"
        } else {
            val lastIndex = selectedDays.toString().length - 1
            val daySubstring = selectedDays.toString().substring(1, 2)
                .uppercase() + selectedDays.toString().substring(2, lastIndex)
            binding.tvNotSchedul.text = daySubstring
        }
    }

}