package com.example.clock.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clock.R
import com.example.clock.databinding.FragmentClockBinding
import com.example.clock.databinding.FragmentLayoutDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class LayoutDialog : Fragment(R.layout.fragment_layout_dialog) {

    private lateinit var binding: FragmentLayoutDialogBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLayoutDialogBinding.bind(view)


        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("")
            .setMessage("Your message goes here. Keep it short but clear.")
            .setPositiveButton("GOT IT") { dialog, which ->
                // Respond to positive button press
            }
            .setNegativeButton("CANCEL") { dialog, which ->
                // Respond to negative button press
            }
            .setView(R.layout.fragment_layout_dialog) // Set a custom view for the dialog
            .show()

    }





}