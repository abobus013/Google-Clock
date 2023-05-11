package com.example.clock.ui.hourglass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clock.R
import com.example.clock.databinding.FragmentAlarmBinding
import com.example.clock.databinding.FragmentHourGlassBinding

class HourGlassFragment : Fragment(R.layout.fragment_hour_glass) {


    private lateinit var binding: FragmentHourGlassBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHourGlassBinding.bind(view)

    }

}