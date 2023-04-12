package com.example.clock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.clock.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavBar = binding.bottomNavBar

        val navController: NavController by lazy {
            val navHost =
                supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
            navHost.navController

        }

        setupWithNavController(bottomNavBar, navController)


    }

}