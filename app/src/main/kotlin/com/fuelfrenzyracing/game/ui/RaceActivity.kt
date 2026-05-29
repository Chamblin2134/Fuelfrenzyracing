package com.fuelfrenzyracing.game.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.fuelfrenzyracing.game.databinding.ActivityRaceBinding
import com.fuelfrenzyracing.game.domain.GameRepository
import com.fuelfrenzyracing.game.domain.GameEngine
import com.fuelfrenzyracing.game.data.database.GameDatabase
import com.fuelfrenzyracing.game.data.models.*
import kotlinx.coroutines.launch

class RaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRaceBinding
    private lateinit var repository: GameRepository
    private val gameEngine = GameEngine()

    private var currentVehicle: Vehicle? = null
    private var currentFuelMix: FuelMix? = null
    private var selectedTrack: RaceTrack? = null
    private var selectedDifficulty: Difficulty = Difficulty.MEDIUM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = GameDatabase.getInstance(this)
        repository = GameRepository(database)

        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        updateTrackDisplay()
        updateDifficultyDisplay()
    }

    private fun setupListeners() {
        binding.btnSelectVehicle.setOnClickListener {
            startActivityForResult(
                Intent(this, VehicleSelectionActivity::class.java),
                VEHICLE_REQUEST_CODE
            )
        }

        binding.btnMixFuel.setOnClickListener {
            startActivityForResult(
                Intent(this, FuelMixActivity::class.java),
                FUEL_REQUEST_CODE
            )
        }

        binding.btnSelectTrack.setOnClickListener {
            showTrackSelection()
        }

        binding.btnDifficultyEasy.setOnClickListener {
            selectedDifficulty = Difficulty.EASY
            updateDifficultyDisplay()
        }

        binding.btnDifficultyMedium.setOnClickListener {
            selectedDifficulty = Difficulty.MEDIUM
            updateDifficultyDisplay()
        }

        binding.btnDifficultyHard.setOnClickListener {
            selectedDifficulty = Difficulty.HARD
            updateDifficultyDisplay()
        }

        binding.btnDifficultyExtreme.setOnClickListener {
            selectedDifficulty = Difficulty.EXTREME
            updateDifficultyDisplay()
        }

        binding.btnStartRace.setOnClickListener {
            if (currentVehicle != null && currentFuelMix != null && selectedTrack != null) {
                startRace()
            } else {
                binding.tvStatus.text = "Please select vehicle, fuel, and track"
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun startRace() {
        lifecycleScope.launch {
            try {
                val vehicleStats = gameEngine.calculateVehicleStats(
                    currentVehicle!!,
                    currentFuelMix!!
                )

                val simulation = gameEngine.simulateRace(
                    vehicleStats,
                    selectedTrack!!,
                    selectedDifficulty
                )

                val coinsEarned = gameEngine.calculateCoinsEarned(1, selectedDifficulty)

                val result = RaceResult(
                    vehicleId = currentVehicle!!.id,
                    trackId = selectedTrack!!.id,
                    timeSeconds = simulation.timeSeconds,
                    topSpeed = simulation.topSpeed,
                    averageSpeed = simulation.averageSpeed,
                    coinsEarned = coinsEarned,
                    position = 1,
                    isNewRecord = false
                )

                repository.saveRaceResult(result)

                val intent = Intent(this@RaceActivity, ResultsActivity::class.java)
                intent.putExtra("time_seconds", simulation.timeSeconds)
                intent.putExtra("top_speed", simulation.topSpeed)
                intent.putExtra("avg_speed", simulation.averageSpeed)
                intent.putExtra("coins_earned", coinsEarned)
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                binding.tvStatus.text = "Race error: ${e.message}"
            }
        }
    }

    private fun updateTrackDisplay() {
        val trackName = selectedTrack?.name ?: "Select Track"
        binding.tvTrackName.text = trackName
    }

    private fun updateDifficultyDisplay() {
        binding.btnDifficultyEasy.isSelected = false
        binding.btnDifficultyMedium.isSelected = false
        binding.btnDifficultyHard.isSelected = false
        binding.btnDifficultyExtreme.isSelected = false

        when (selectedDifficulty) {
            Difficulty.EASY -> binding.btnDifficultyEasy.isSelected = true
            Difficulty.MEDIUM -> binding.btnDifficultyMedium.isSelected = true
            Difficulty.HARD -> binding.btnDifficultyHard.isSelected = true
            Difficulty.EXTREME -> binding.btnDifficultyExtreme.isSelected = true
        }
    }

    private fun showTrackSelection() {
        val tracks = listOf(
            RaceTrack("track_1", "Desert Sprint", difficulty = Difficulty.EASY, rewardCoins = 100),
            RaceTrack("track_2", "Mountain Pass", difficulty = Difficulty.MEDIUM, rewardCoins = 150),
            RaceTrack("track_3", "Urban Chase", difficulty = Difficulty.HARD, rewardCoins = 200),
            RaceTrack("track_4", "Highway Extreme", difficulty = Difficulty.EXTREME, rewardCoins = 300)
        )

        val trackNames = tracks.map { it.name }.toTypedArray()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Track")
            .setItems(trackNames) { _, which ->
                selectedTrack = tracks[which]
                updateTrackDisplay()
            }
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                VEHICLE_REQUEST_CODE -> {
                    // Handle vehicle selection
                }
                FUEL_REQUEST_CODE -> {
                    // Handle fuel mix selection
                }
            }
        }
    }

    companion object {
        const val VEHICLE_REQUEST_CODE = 1001
        const val FUEL_REQUEST_CODE = 1002
    }
}
