package com.fuelfrenzyracing.game.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val baseSpeed: Float,
    val baseAcceleration: Float,
    val baseHandling: Float,
    val weight: Float,
    val isUnlocked: Boolean = false
)

@Entity(tableName = "parts")
data class PartEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val speedBoost: Float = 0f,
    val accelerationBoost: Float = 0f,
    val handlingBoost: Float = 0f,
    val weight: Float = 0f,
    val cost: Int = 0
)

@Entity(tableName = "fuel_ingredients")
data class FuelIngredientEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val speedBoost: Float = 0f,
    val accelerationBoost: Float = 0f,
    val fuelConsumption: Float = 1f,
    val weight: Float = 0f,
    val description: String = ""
)

@Entity(tableName = "race_results")
data class RaceResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val vehicleId: String,
    val trackId: String,
    val timeSeconds: Float,
    val topSpeed: Float,
    val averageSpeed: Float,
    val coinsEarned: Int,
    val position: Int,
    val isNewRecord: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
