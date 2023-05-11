package com.example.clock.ui.alarm

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.clock.R
import com.example.clock.data.models.AlarmData
import com.example.clock.databinding.ItemLayoutBinding


class AlarmAdapter(
    private var onUpdateClickListener: ((AlarmData) -> Unit)? = null,
) :ListAdapter<AlarmData, AlarmAdapter.AlarmViewHolder>(MyDiffUtil) {

    private var onDayClickListener: ((AlarmData) -> Unit)? = null

    private var onDeleteClickListener: ((AlarmData) -> Unit)? = null

    private var onDatPickerClickListener: ((ItemLayoutBinding) -> Unit)? = null

    private var onDescriptionClickListener: ((ItemLayoutBinding) -> Unit)? = null

    private var onNotificationClickListener: ((ItemLayoutBinding) -> Unit)? = null


    fun setOnDescriptionClickListener(block: (ItemLayoutBinding) -> Unit) {
        onDescriptionClickListener = block
    }

    fun setOnDeleteClickListener(block: (AlarmData) -> Unit) {
        onDeleteClickListener = block
    }

    fun setOnUpdateClickListener(block: (AlarmData) -> Unit) {
        onUpdateClickListener = block
    }


    fun setOnDataPickerClickListener(block: (ItemLayoutBinding) -> Unit) {
        onDatPickerClickListener = block
    }

    fun setOnNotificationClickListener(block: (ItemLayoutBinding) -> Unit) {
        onNotificationClickListener = block
    }
    fun setOnDayClickListener(block: (AlarmData) -> Unit) {
        onDayClickListener = block
    }

    inner class AlarmViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {

            val alarmData = getItem(position)
            binding.tvTime.text = alarmData.time

            daysToTextView(alarmData, binding)

            binding.tvMonday.isSelected = alarmData.monday
            binding.tvTuesday.isSelected = alarmData.tuesday
            binding.tvWednesday.isSelected = alarmData.wednesday
            binding.tvThursday.isSelected = alarmData.thursday
            binding.tvFriday.isSelected = alarmData.friday
            binding.tvSaturday.isSelected = alarmData.saturday
            binding.tvSunday.isSelected = alarmData.sunday


            binding.tvDescription.setOnClickListener {
                onDescriptionClickListener?.invoke(binding)

            }

            binding.root.setOnClickListener {
                onDeleteClickListener?.invoke(alarmData)
            }

            binding.ivArrow.setOnClickListener {
                if (binding.expandedL.isExpanded) {
                    binding.expandedL.collapse()
                    binding.secondExpandedL.collapse()
                    binding.ivArrow.setImageResource(R.drawable.arrow_bottom)


                } else {
                    binding.expandedL.toggle()
                    binding.secondExpandedL.toggle()
                    binding.ivArrow.setImageResource(R.drawable.arrow_top)
                }
            }

            binding.root.setOnClickListener {
                if (binding.expandedL.isExpanded) {
                    binding.expandedL.collapse()
                    binding.secondExpandedL.collapse()
                    binding.ivArrow.setImageResource(R.drawable.arrow_bottom)
                } else {
                    binding.expandedL.toggle()
                    binding.secondExpandedL.toggle()
                    binding.ivArrow.setImageResource(R.drawable.arrow_top)
                }
            }

            binding.tvNotification.setOnClickListener {
                onNotificationClickListener?.invoke(binding)
            }

            binding.tvDelete.setOnClickListener {
                onDeleteClickListener?.invoke(alarmData)
            }

            binding.tvTime.setOnClickListener {
                onUpdateClickListener?.invoke(alarmData)
            }

            binding.tvAddDataAlarm.setOnClickListener {
                onDatPickerClickListener?.invoke(binding)
            }


            binding.tvMonday.setOnClickListener {
                binding.tvMonday.isSelected = alarmData.monday.not()
                alarmData.monday = alarmData.monday.not()
                onDayClickListener?.invoke(alarmData)
                daysToTextView(alarmData, binding)

            }

            binding.tvTuesday.setOnClickListener {
                binding.tvTuesday.isSelected = alarmData.tuesday.not()
                alarmData.tuesday = alarmData.tuesday.not()
                onDayClickListener?.invoke(alarmData)
                daysToTextView(alarmData, binding)
            }

            binding.tvWednesday.setOnClickListener {
                binding.tvWednesday.isSelected = alarmData.wednesday.not()
                alarmData.wednesday = alarmData.wednesday.not()
                onDayClickListener?.invoke(alarmData)
                daysToTextView(alarmData, binding)
            }

            binding.tvThursday.setOnClickListener {
                binding.tvThursday.isSelected = alarmData.thursday.not()
                alarmData.thursday = alarmData.thursday.not()
                onDayClickListener?.invoke(alarmData)
                daysToTextView(alarmData, binding)
            }

            binding.tvFriday.setOnClickListener {
                binding.tvFriday.isSelected = alarmData.friday.not()
                alarmData.friday = alarmData.friday.not()
                onDayClickListener?.invoke(alarmData)
                daysToTextView(alarmData, binding)
            }

            binding.tvSaturday.setOnClickListener {
                binding.tvSaturday.isSelected = alarmData.saturday.not()
                alarmData.saturday = alarmData.saturday.not()
                onDayClickListener?.invoke(alarmData)
                daysToTextView(alarmData, binding)
            }


            binding.tvSunday.setOnClickListener {
                binding.tvSunday.isSelected = alarmData.sunday.not()
                alarmData.sunday = alarmData.sunday.not()
                onDayClickListener?.invoke(alarmData)
                daysToTextView(alarmData, binding)
            }



        }


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        return AlarmViewHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(position)
    }

    @SuppressLint("SetTextI18n")
    fun daysToTextView(alarmData: AlarmData, binding: ItemLayoutBinding) {
        val isActivatedDaysList = booleanArrayOf(
            alarmData.monday, alarmData.tuesday,
            alarmData.wednesday, alarmData.thursday, alarmData.friday,
            alarmData.saturday, alarmData.sunday
        )
        val weekDays = listOf(
            "Понедельник",
            "Вторник",
            "Среда",
            "Четверг",
            "Пятница",
            "Суббота",
            "Воскресенье"
        )
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

    private object MyDiffUtil : DiffUtil.ItemCallback<AlarmData>() {
        override fun areItemsTheSame(oldItem: AlarmData, newItem: AlarmData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AlarmData, newItem: AlarmData): Boolean {
            return oldItem.id == newItem.id && oldItem.time == newItem.time

        }

    }

    fun removeItem(item: AlarmData){
        val currentList =  currentList.toMutableList()
        currentList.remove(item)
        submitList(currentList)

    }

}