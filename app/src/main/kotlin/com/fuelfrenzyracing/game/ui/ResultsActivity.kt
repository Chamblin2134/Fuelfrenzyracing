package com.fuelfrenzyracing.game.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fuelfrenzyracing.game.databinding.ActivityResultsBinding

class ResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayResults()
        setupListeners()
    }

    private fun displayResults() {
        val timeSeconds = intent.getFloatExtra("time_seconds", 0f)
        val topSpeed = intent.getFloatExtra("top_speed", 0f)
        val avgSpeed = intent.getFloatExtra("avg_speed", 0f)
        val coinsEarned = intent.getIntExtra("coins_earned", 0)

        binding.tvTime.text = String.format("Time: %.2f seconds", timeSeconds)
        binding.tvTopSpeed.text = String.format("Top Speed: %.1f mph", topSpeed)
        binding.tvAvgSpeed.text = String.format("Avg Speed: %.1f mph", avgSpeed)
        binding.tvCoinsEarned.text = "Coins Earned: $coinsEarned"
        binding.tvPosition.text = "1st Place!"
    }

    private fun setupListeners() {
        binding.btnRaceAgain.setOnClickListener {
            startActivity(Intent(this, RaceActivity::class.java))
            finish()
        }

        binding.btnReturnToMenu.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
