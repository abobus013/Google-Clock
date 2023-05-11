package com.example.clock.ui.alarm


import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clock.notifications.receivers.AlarmReceiver
import com.example.clock.R
import com.example.clock.data.models.AlarmData
import com.example.clock.data.locals.AlarmDao
import com.example.clock.databinding.FragmentAlarmBinding
import com.example.clock.data.locals.AlarmDataBase
import com.example.clock.databinding.DialogEditDescriptionBinding
import com.example.clock.databinding.ItemLayoutBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch

class AlarmFragment : Fragment(R.layout.fragment_alarm) {

    private lateinit var binding: FragmentAlarmBinding
    private val adapter = AlarmAdapter()
    private lateinit var dao: AlarmDao
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlarmBinding.bind(view)

        initVariables()
        initListeners()
    }

    private fun initVariables() {
        dao = AlarmDataBase.getInstance(requireContext()).getAlarmDao()
        binding.recyclerView.adapter = adapter


        lifecycleScope.launch {
            adapter.submitList(dao.getListOfAlarms().toMutableList())
        }

    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun initListeners() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.recyclerView.layoutManager!! as LinearLayoutManager
                if (layoutManager.findFirstCompletelyVisibleItemPosition() > 0) {
                    binding.topBar.alpha = 1F
                    val window = requireActivity().window
                    window.statusBarColor =
                        ContextCompat.getColor(requireContext(), R.color.light_top_bar_color)
                } else {
                    binding.topBar.alpha = 0F
                    val window = requireActivity().window
                    window.statusBarColor =
                        ContextCompat.getColor(requireContext(), R.color.bg_color)
                }
            }
        })


        adapter.setOnNotificationClickListener {
            lifecycleScope.launch {

                val intent = Intent(requireContext(), AlarmReceiver::class.java)

                val pendingIntent = PendingIntent.getBroadcast(
                    requireContext(),
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
                )

                val alarmManager =
                    requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.setAlarmClock(
                    AlarmManager.AlarmClockInfo(System.currentTimeMillis() + 5000, pendingIntent),
                    pendingIntent
                )

            }
        }


        adapter.setOnDeleteClickListener {
            lifecycleScope.launch {
                adapter.removeItem(it)
                dao.deleteAlarm(it)
            }


        }

        adapter.setOnDayClickListener {
            lifecycleScope.launch {
                dao.updateAlarms(it)
            }
        }

        adapter.setOnDescriptionClickListener { itemBinding ->
            lifecycleScope.launch {

                val dialogBinding = DialogEditDescriptionBinding.inflate(layoutInflater)

                val builder = MaterialAlertDialogBuilder(
                    requireContext(), R.style.ThemeOverlay_App_MaterialAlertDialog
                )

                builder.setView(dialogBinding.root).setBackground(
                        ContextCompat.getDrawable(
                            requireContext(), R.drawable.dialog_bg
                        )
                    ).setPositiveButton("ОК") { _, _ ->

                        val sharedPreferences =
                            requireActivity().getPreferences(Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString(
                            "description_text", dialogBinding.edEditText.text.toString()
                        )
                        editor.apply()

                        Log.d("TTTTT", " Текст сохраняется")
                        itemBinding.tvDescription.text = dialogBinding.edEditText.text.toString()

                    }.setNegativeButton("Отмена") { dialog, _ ->
                        dialog.dismiss()
                    }.create()

                val dialog = builder.create()
                dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
                dialogBinding.edEditText.setText(itemBinding.tvDescription.text.toString())
                dialogBinding.lEdittext.showKeyboard()

                dialog.setOnDismissListener {
                    hideKeyboard(dialogBinding.edEditText)
                }
                dialog.show()
            }


        }

        binding.ivAdd.setOnClickListener {

            val materialTimePicker =
                MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(12)
                    .setTheme(R.style.MyCustomTimePicker).setMinute(0)
                    .setInputMode(INPUT_MODE_CLOCK).setTitleText("Выберите время").build()

            materialTimePicker.show(childFragmentManager, "tag")
            materialTimePicker.addOnPositiveButtonClickListener {
                lifecycleScope.launch {
                    if (materialTimePicker.minute < 10) {

                        if (materialTimePicker.hour < 10) {
                            dao.addAlarms(
                                AlarmData(
                                    0, "0${materialTimePicker.hour}:0${materialTimePicker.minute}", label = ""
                                )
                            )
                        } else {
                            dao.addAlarms(
                                AlarmData(
                                    0, "${materialTimePicker.hour}:0${materialTimePicker.minute}", label = ""
                                )
                            )
                        }

                    } else {
                        if (materialTimePicker.hour < 10) {
                            dao.addAlarms(
                                AlarmData(
                                    0, "0${materialTimePicker.hour}:${materialTimePicker.minute}", label = ""
                                )
                            )
                        } else {
                            dao.addAlarms(
                                AlarmData(
                                    0, "${materialTimePicker.hour}:${materialTimePicker.minute}", label = ""
                                )
                            )
                        }

                    }

                    adapter.submitList(dao.getListOfAlarms().toMutableList().sortedBy { it.time }
                        .toMutableList())

                }
            }

        }

        adapter.setOnUpdateClickListener { time ->
            val updateMaterialTimePicker =
                MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(12)
                    .setTheme(R.style.MyCustomTimePicker).setMinute(0)
                    .setInputMode(INPUT_MODE_CLOCK).setTitleText("Выберите время").build()

            updateMaterialTimePicker.show(childFragmentManager, "tag")
            updateMaterialTimePicker.addOnPositiveButtonClickListener {
                lifecycleScope.launch {
                    if (updateMaterialTimePicker.minute < 10) {

                        if (updateMaterialTimePicker.hour < 10) {
                            dao.updateAlarms(
                                AlarmData(
                                    time.id,
                                    "0${updateMaterialTimePicker.hour}:0${updateMaterialTimePicker.minute}", label = time.label
                                )
                            )
                        } else {
                            dao.updateAlarms(
                                AlarmData(
                                    time.id,
                                    "${updateMaterialTimePicker.hour}:0${updateMaterialTimePicker.minute}", label = time.label
                                )
                            )
                        }
                    } else {
                        if (updateMaterialTimePicker.minute < 10) {
                            dao.updateAlarms(
                                AlarmData(
                                    time.id,
                                    "0${updateMaterialTimePicker.hour}:${updateMaterialTimePicker.minute}", label = time.label
                                )
                            )
                        } else {
                            dao.updateAlarms(
                                AlarmData(
                                    time.id,
                                    "${updateMaterialTimePicker.hour}:${updateMaterialTimePicker.minute}", label = time.label
                                )
                            )
                        }
                    }

                    adapter.submitList(dao.getListOfAlarms().toMutableList().sortedBy { it.time }
                        .toMutableList())

                }
            }
        }

        adapter.setOnDataPickerClickListener {
            val dataPicker = MaterialDatePicker.Builder.datePicker().setTitleText("Выбранная Дата")
                .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR).build()

            dataPicker.show(childFragmentManager, "TAG")


        }

    }

    fun Context.showKeyboard(editText: EditText) {
        val inputMethodManager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInputFromWindow(
            editText.applicationWindowToken, InputMethodManager.SHOW_IMPLICIT, 0
        )
        editText.requestFocus()
        editText.setSelection(editText.text.length)
    }

    private fun hideKeyboard(view: View) {
        getInputMethodManager(view).hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getInputMethodManager(view: View): InputMethodManager {
        val context = view.context
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun View.showKeyboard(
    ) {
        isFocusable = true
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

}

