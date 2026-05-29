package com.fuelfrenzyracing.game.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fuelfrenzyracing.game.databinding.ActivityGarageBinding
import com.fuelfrenzyracing.game.domain.GameRepository
import com.fuelfrenzyracing.game.data.database.GameDatabase
import com.fuelfrenzyracing.game.ui.adapters.VehicleAdapter
import kotlinx.coroutines.launch

class GarageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGarageBinding
    private lateinit var repository: GameRepository
    private lateinit var vehicleAdapter: VehicleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGarageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = GameDatabase.getInstance(this)
        repository = GameRepository(database)

        setupRecyclerView()
        loadVehicles()
        setupListeners()
    }

    private fun setupRecyclerView() {
        vehicleAdapter = VehicleAdapter { vehicle ->
            val intent = Intent(this, VehicleCustomizeActivity::class.java)
            intent.putExtra("vehicle_id", vehicle.id)
            startActivity(intent)
        }
        binding.rvVehicles.apply {
            layoutManager = LinearLayoutManager(this@GarageActivity)
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

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}
