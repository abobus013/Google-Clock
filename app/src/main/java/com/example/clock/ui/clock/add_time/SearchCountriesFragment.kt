package com.example.clock.ui.clock.add_time

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.clock.MainActivity
import com.example.clock.R
import com.example.clock.data.locals.AlarmDataBase
import com.example.clock.data.locals.SearchCountryDao
import com.example.clock.data.models.CountryTime
import com.example.clock.databinding.FragmentSearchCountriesBinding
import com.example.clock.ui.clock.adapters.CountryTimeAdapter
import kotlinx.coroutines.launch


class SearchCountriesFragment : Fragment(R.layout.fragment_search_countries) {

    private lateinit var binding: FragmentSearchCountriesBinding
    private lateinit var adapter: CountryTimeAdapter
    private lateinit var dao: SearchCountryDao



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchCountriesBinding.bind(view)

        initVariables()
        initListeners()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MainActivity).showOrHideVisibility(true)
    }

    private fun initVariables() {
        dao = AlarmDataBase.getInstance(requireContext()).getSearchCountryDao()
        val dbHelper = DBHelper(requireContext())
        val countryList = dbHelper.getAllCountries()

        adapter = CountryTimeAdapter(countryList)
        binding.rvCountries.adapter = adapter

    }

    private fun initListeners() {

        binding.ivArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etSearch.addTextChangedListener {

            lifecycleScope.launch {
                binding.etSearch.setOnFocusChangeListener { _, hasFocus -> // изменение видимости RecyclerView при фокусировке на EditText
                    binding.rvCountries.visibility = if (hasFocus) View.VISIBLE else View.GONE
                }
            }
        }

    }

}
