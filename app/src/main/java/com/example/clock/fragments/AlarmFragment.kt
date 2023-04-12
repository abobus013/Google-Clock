package com.example.clock.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clock.R
import com.example.clock.data.ClockData
import com.example.clock.data.ClocksDao
import com.example.clock.databinding.FragmentAlarmBinding
import com.example.clock.adapter.ClocksAdapter
import com.example.clock.data.DataBase
import com.example.clock.databinding.FragmentLayoutDialogBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch

class AlarmFragment : Fragment(R.layout.fragment_alarm) {

    private lateinit var binding: FragmentAlarmBinding
    private val adapter = ClocksAdapter()
    private lateinit var dao: ClocksDao
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlarmBinding.bind(view)


        initVariables()
        initListeners()
    }

    private fun initVariables() {
        dao = DataBase.getInstance(requireContext()).getClocksDao()
        binding.recyclerView.adapter = adapter


        lifecycleScope.launch {
            adapter.models = dao.getListOfClocks().toMutableList()
        }

    }


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
                            dao.addClocks(
                                ClockData(
                                    0, "0${materialTimePicker.hour}:0${materialTimePicker.minute}"
                                )
                            )
                        } else {
                            dao.addClocks(
                                ClockData(
                                    0, "${materialTimePicker.hour}:0${materialTimePicker.minute}"
                                )
                            )
                        }

                    } else {
                        if (materialTimePicker.hour < 10) {
                            dao.addClocks(
                                ClockData(
                                    0, "0${materialTimePicker.hour}:${materialTimePicker.minute}"
                                )
                            )
                        } else {
                            dao.addClocks(
                                ClockData(
                                    0, "${materialTimePicker.hour}:${materialTimePicker.minute}"
                                )
                            )
                        }

                    }

                    adapter.models =
                        dao.getListOfClocks().toMutableList().sortedBy { it.time }.toMutableList()

                }
            }


        }


        adapter.setOnDeleteClickListener {
            lifecycleScope.launch {
                dao.deleteClock(it)
            }
        }

        adapter.setOnDayClickListener {
            lifecycleScope.launch {
                dao.updateClocks(it)
            }
        }

        adapter.setOnDescriptionClickListener {

            val dialogBinding = FragmentLayoutDialogBinding.inflate(layoutInflater)


            val builder = MaterialAlertDialogBuilder(
                requireContext(),
                R.style.ThemeOverlay_App_MaterialAlertDialog
            )
            builder.setTitle("")
                .setView(dialogBinding.root)
                .setMessage("")
                .setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.dialog_bg))

                .setPositiveButton("ОК") { _, _ ->
                    Log.d("TTTTT", "$builder Текст сохраняется")
                    dialogBinding.edEditText.setText(it.tvDescription?.toString())

                }.setNegativeButton("Отмена") { dialog, _ ->
                    // Respond to negative button press
                    dialog.dismiss()
                }
                .create()

            dialogBinding.edEditText.requestFocus()
            showKeyboard(dialogBinding.edEditText)

            builder.setOnDismissListener {
                hideKeyboard(dialogBinding.edEditText )
            }

            builder.show()

        }




        adapter.setOnDataPickerClickListener {
            val dataPicker = MaterialDatePicker.Builder.datePicker().setTitleText("Выбранная Дата")
                .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR).build()

            dataPicker.show(childFragmentManager, "TAG")
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
                            dao.updateClocks(
                                ClockData(
                                    time.id,
                                    "0${updateMaterialTimePicker.hour}:0${updateMaterialTimePicker.minute}"
                                )
                            )
                        } else {
                            dao.updateClocks(
                                ClockData(
                                    time.id,
                                    "${updateMaterialTimePicker.hour}:0${updateMaterialTimePicker.minute}"
                                )
                            )
                        }
                    } else {
                        if (updateMaterialTimePicker.minute < 10) {
                            dao.updateClocks(
                                ClockData(
                                    time.id,
                                    "0${updateMaterialTimePicker.hour}:${updateMaterialTimePicker.minute}"
                                )
                            )
                        } else {
                            dao.updateClocks(
                                ClockData(
                                    time.id,
                                    "${updateMaterialTimePicker.hour}:${updateMaterialTimePicker.minute}"
                                )
                            )
                        }
                    }

                    adapter.models =
                        dao.getListOfClocks().toMutableList().sortedBy { it.time }.toMutableList()
                }
            }
        }


    }

    private fun showKeyboard(view: View) {
        view.post {
            getInputMethodManager(view).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideKeyboard(view: View) {
        getInputMethodManager(view).hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getInputMethodManager(view: View): InputMethodManager {
        val context = view.context
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }


}

