package com.fuelfrenzyracing.game.data.models

data class FuelIngredient(
    val id: String,
    val name: String,
    val category: IngredientCategory,
    val speedBoost: Float = 0f,
    val accelerationBoost: Float = 0f,
    val fuelConsumption: Float = 1f,
    val weight: Float = 0f,
    val description: String = ""
)

enum class IngredientCategory {
    CONDIMENT,
    SAUCE,
    BEVERAGE,
    ENERGY
}

data class FuelMix(
    val id: String,
    val name: String,
    val ingredients: List<FuelIngredient>,
    val totalSpeedBoost: Float = 0f,
    val totalAccelerationBoost: Float = 0f,
    val totalFuelConsumption: Float = 1f,
    val totalWeight: Float = 0f
) {
    fun calculateStats(): FuelMixStats {
        return FuelMixStats(
            speedBoost = ingredients.sumOf { it.speedBoost.toDouble() }.toFloat(),
            accelerationBoost = ingredients.sumOf { it.accelerationBoost.toDouble() }.toFloat(),
            fuelConsumption = ingredients.fold(1f) { acc, ingredient -> acc * ingredient.fuelConsumption },
            weight = ingredients.sumOf { it.weight.toDouble() }.toFloat()
        )
    }
}

data class FuelMixStats(
    val speedBoost: Float,
    val accelerationBoost: Float,
    val fuelConsumption: Float,
    val weight: Float
)
