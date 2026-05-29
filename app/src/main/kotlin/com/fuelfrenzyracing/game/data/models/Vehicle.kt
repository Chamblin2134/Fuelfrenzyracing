package com.fuelfrenzyracing.game.data.models

data class Vehicle(
    val id: String,
    val name: String,
    val type: VehicleType,
    val baseSpeed: Float,
    val baseAcceleration: Float,
    val baseHandling: Float,
    val weight: Float,
    val parts: MutableList<Part> = mutableListOf(),
    val isUnlocked: Boolean = false
)

enum class VehicleType(val displayName: String) {
    CAR("Car"),
    TRUCK("Truck"),
    SEMI("Semi")
}

data class Part(
    val id: String,
    val name: String,
    val type: PartType,
    val speedBoost: Float = 0f,
    val accelerationBoost: Float = 0f,
    val handlingBoost: Float = 0f,
    val weight: Float = 0f,
    val cost: Int = 0
)

enum class PartType {
    ENGINE,
    TURBO,
    SUSPENSION,
    TIRES,
    EXHAUST,
    NITROUS,
    BRAKES,
    TRANSMISSION
}
