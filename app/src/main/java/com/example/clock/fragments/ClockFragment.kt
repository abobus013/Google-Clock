package com.example.clock.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.View

import com.example.clock.R

import com.example.clock.databinding.FragmentClockBinding
import java.text.SimpleDateFormat

import java.util.*

class ClockFragment : Fragment(R.layout.fragment_clock) {

    private lateinit var binding: FragmentClockBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClockBinding.bind(view)

        val date = getCurrentDateTime()
        val dateInString = date.toString("ww/dd/MM")

    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}