package com.fuelfrenzyracing.game.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.fuelfrenzyracing.game.databinding.ActivityFuelMixBinding
import com.fuelfrenzyracing.game.domain.GameRepository
import com.fuelfrenzyracing.game.data.database.GameDatabase
import com.fuelfrenzyracing.game.ui.adapters.IngredientAdapter
import kotlinx.coroutines.launch

class FuelMixActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFuelMixBinding
    private lateinit var repository: GameRepository
    private lateinit var ingredientAdapter: IngredientAdapter
    private val selectedIngredients = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFuelMixBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = GameDatabase.getInstance(this)
        repository = GameRepository(database)

        setupRecyclerView()
        loadIngredients()
        setupListeners()
    }

    private fun setupRecyclerView() {
        ingredientAdapter = IngredientAdapter { ingredient, isSelected ->
            if (isSelected) {
                selectedIngredients.add(ingredient.id)
            } else {
                selectedIngredients.remove(ingredient.id)
            }
            updateMixStats()
        }
        binding.rvIngredients.apply {
            layoutManager = GridLayoutManager(this@FuelMixActivity, 2)
            adapter = ingredientAdapter
        }
    }

    private fun loadIngredients() {
        lifecycleScope.launch {
            repository.getAllIngredients().collect { ingredients ->
                ingredientAdapter.updateData(ingredients)
            }
        }
    }

    private fun setupListeners() {
        binding.btnClearMix.setOnClickListener {
            selectedIngredients.clear()
            ingredientAdapter.clearSelection()
            updateMixStats()
        }

        binding.btnSaveMix.setOnClickListener {
            if (selectedIngredients.isNotEmpty()) {
                setResult(RESULT_OK)
                finish()
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun updateMixStats() {
        val selectedCount = selectedIngredients.size
        binding.tvIngredientCount.text = "Selected: $selectedCount"

        if (selectedCount > 0) {
            binding.tvMixStats.text = "Mix power increasing..."
        } else {
            binding.tvMixStats.text = "Select ingredients to create fuel mix"
        }
    }
}
