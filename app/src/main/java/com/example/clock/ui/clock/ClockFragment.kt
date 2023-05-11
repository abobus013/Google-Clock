package com.example.clock.ui.clock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.clock.MainActivity
import com.example.clock.R
import com.example.clock.data.locals.AlarmDataBase
import com.example.clock.data.locals.ClockDao
import com.example.clock.databinding.FragmentClockBinding
import com.example.clock.ui.clock.adapters.ClockAdapter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ClockFragment : Fragment(R.layout.fragment_clock) {


    private lateinit var binding: FragmentClockBinding
    private val adapter = ClockAdapter()
    private lateinit var dao: ClockDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClockBinding.bind(view)

        initVariables()
        initListeners()

    }

    private fun initListeners() {
        binding.ivAddCountryTimes.setOnClickListener {
            findNavController().navigate(ClockFragmentDirections.actionFragmentClockToDialogFragment())
            (requireActivity() as MainActivity).showOrHideVisibility(false)

        }
    }

    private fun initVariables() {
        dao = AlarmDataBase.getInstance(requireContext()).getClocksDao()
        binding.clockRv.adapter = adapter

        val date = getCurrentDateTime()
        date.toString("ww/dd/MM")

        lifecycleScope.launch {
            adapter.submitList(dao.getListOfClocks().toMutableList())
        }

    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }



}