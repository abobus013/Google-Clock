package com.example.clock.ui.timer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clock.R
import com.example.clock.databinding.FragmentAlarmBinding
import com.example.clock.databinding.FragmentTimerBinding

class TimerFragment : Fragment(R.layout.fragment_timer) {

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_timer, container, false)
//    }

    private lateinit var binding: FragmentTimerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimerBinding.bind(view)

    }

}