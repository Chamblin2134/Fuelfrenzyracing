package com.fuelfrenzyracing.game.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fuelfrenzyracing.game.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        binding.btnGarage.setOnClickListener {
            startActivity(Intent(this, GarageActivity::class.java))
        }

        binding.btnRace.setOnClickListener {
            startActivity(Intent(this, RaceActivity::class.java))
        }

        binding.btnStats.setOnClickListener {
            // TODO: Show stats screen
        }

        binding.btnSettings.setOnClickListener {
            // TODO: Show settings screen
        }
    }
}
