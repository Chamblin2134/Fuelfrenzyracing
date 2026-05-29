package com.fuelfrenzyracing.game.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fuelfrenzyracing.game.databinding.ActivityVehicleCustomizeBinding
import com.fuelfrenzyracing.game.domain.GameRepository
import com.fuelfrenzyracing.game.data.database.GameDatabase
import kotlinx.coroutines.launch

class VehicleCustomizeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVehicleCustomizeBinding
    private lateinit var repository: GameRepository
    private val vehicleId: String by lazy { intent.getStringExtra("vehicle_id") ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleCustomizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = GameDatabase.getInstance(this)
        repository = GameRepository(database)

        loadVehicleDetails()
        setupListeners()
    }

    private fun loadVehicleDetails() {
        lifecycleScope.launch {
            val vehicle = repository.getVehicleById(vehicleId)
            if (vehicle != null) {
                binding.tvVehicleName.text = vehicle.name
                binding.tvVehicleType.text = vehicle.type
                binding.tvBaseSpeed.text = "Speed: ${vehicle.baseSpeed}"
                binding.tvAcceleration.text = "Acceleration: ${vehicle.baseAcceleration}"
                binding.tvHandling.text = "Handling: ${vehicle.baseHandling}"
            }
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}
