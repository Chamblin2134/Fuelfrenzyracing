package com.fuelfrenzyracing.game.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fuelfrenzyracing.game.databinding.ActivityVehicleSelectionBinding
import com.fuelfrenzyracing.game.domain.GameRepository
import com.fuelfrenzyracing.game.data.database.GameDatabase
import com.fuelfrenzyracing.game.ui.adapters.VehicleAdapter
import kotlinx.coroutines.launch

class VehicleSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVehicleSelectionBinding
    private lateinit var repository: GameRepository
    private lateinit var vehicleAdapter: VehicleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = GameDatabase.getInstance(this)
        repository = GameRepository(database)

        setupRecyclerView()
        loadVehicles()
    }

    private fun setupRecyclerView() {
        vehicleAdapter = VehicleAdapter { vehicle ->
            setResult(RESULT_OK)
            finish()
        }
        binding.rvVehicles.apply {
            layoutManager = LinearLayoutManager(this@VehicleSelectionActivity)
            adapter = vehicleAdapter
        }
    }

    private fun loadVehicles() {
        lifecycleScope.launch {
            repository.getAllVehicles().collect { vehicles ->
                vehicleAdapter.updateData(vehicles)
            }
        }
    }
}
