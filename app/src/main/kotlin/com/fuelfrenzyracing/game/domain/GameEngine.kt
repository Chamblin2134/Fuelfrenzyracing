package com.fuelfrenzyracing.game.domain

import com.fuelfrenzyracing.game.data.models.*

class GameEngine {

    fun calculateVehicleStats(
        vehicle: Vehicle,
        fuelMix: FuelMix
    ): VehicleStats {
        val partBoosts = vehicle.parts.fold(PartBoosts()) { acc, part ->
            acc.copy(
                speedBoost = acc.speedBoost + part.speedBoost,
                accelerationBoost = acc.accelerationBoost + part.accelerationBoost,
                handlingBoost = acc.handlingBoost + part.handlingBoost,
                totalWeight = acc.totalWeight + part.weight
            )
        }

        val fuelStats = fuelMix.calculateStats()

        val finalSpeed = vehicle.baseSpeed * (1 + partBoosts.speedBoost + fuelStats.speedBoost)
        val finalAcceleration =
            vehicle.baseAcceleration * (1 + partBoosts.accelerationBoost + fuelStats.accelerationBoost)
        val finalHandling = vehicle.baseHandling * (1 + partBoosts.handlingBoost)
        val totalWeight = vehicle.weight + partBoosts.totalWeight + fuelStats.weight

        return VehicleStats(
            maxSpeed = finalSpeed,
            acceleration = finalAcceleration,
            handling = finalHandling,
            weight = totalWeight,
            fuelConsumption = fuelStats.fuelConsumption
        )
    }

    fun simulateRace(
        vehicleStats: VehicleStats,
        track: RaceTrack,
        difficulty: Difficulty
    ): RaceSimulation {
        val difficulties = mapOf(
            Difficulty.EASY to 1.0f,
            Difficulty.MEDIUM to 0.85f,
            Difficulty.HARD to 0.70f,
            Difficulty.EXTREME to 0.50f
        )

        val difficultyMultiplier = difficulties[difficulty] ?: 1.0f
        val adjustedMaxSpeed = vehicleStats.maxSpeed * difficultyMultiplier
        val adjustedAcceleration = vehicleStats.acceleration * difficultyMultiplier
        val adjustedHandling = vehicleStats.handling * difficultyMultiplier

        var currentDistance = 0f
        var currentSpeed = 0f
        var totalTime = 0f
        var topSpeed = 0f
        var totalSpeedSum = 0f
        var speedReadings = 0

        val segments = 100
        val segmentDistance = track.distance / segments

        for (i in 0 until segments) {
            val timeToAccelerate =
                (adjustedMaxSpeed - currentSpeed) / (adjustedAcceleration + 0.1f)
            val distanceDuringAccel = currentSpeed * timeToAccelerate + 0.5f * adjustedAcceleration * timeToAccelerate * timeToAccelerate

            currentSpeed = minOf(adjustedMaxSpeed, currentSpeed + adjustedAcceleration * timeToAccelerate)

            val curveEffect = calculateCurveEffect(currentDistance, track.curves, adjustedHandling)
            currentSpeed *= curveEffect

            currentDistance += segmentDistance
            totalTime += timeToAccelerate

            topSpeed = maxOf(topSpeed, currentSpeed)
            totalSpeedSum += currentSpeed
            speedReadings++
        }

        val averageSpeed = if (speedReadings > 0) totalSpeedSum / speedReadings else 0f

        val weatherMultiplier = when (track.weatherCondition) {
            WeatherCondition.CLEAR -> 1.0f
            WeatherCondition.RAIN -> 0.9f
            WeatherCondition.FOG -> 0.85f
            WeatherCondition.STORMY -> 0.75f
        }

        totalTime *= (1.0f / weatherMultiplier)

        return RaceSimulation(
            timeSeconds = totalTime,
            topSpeed = topSpeed * weatherMultiplier,
            averageSpeed = averageSpeed * weatherMultiplier
        )
    }

    private fun calculateCurveEffect(
        position: Float,
        curves: List<TrackCurve>,
        handling: Float
    ): Float {
        val relevantCurve = curves.find { it.position <= position && it.position + 500 > position }
        return if (relevantCurve != null) {
            val curveSeverity = relevantCurve.severity
            val handlingBonus = minOf(0.2f, handling * 0.1f)
            1f - (curveSeverity * 0.3f * (1f - handlingBonus))
        } else {
            1f
        }
    }

    fun calculateCoinsEarned(position: Int, difficulty: Difficulty): Int {
        val baseReward = when (position) {
            1 -> 100
            2 -> 60
            3 -> 40
            else -> 20
        }

        val difficultyMultiplier = when (difficulty) {
            Difficulty.EASY -> 1.0f
            Difficulty.MEDIUM -> 1.5f
            Difficulty.HARD -> 2.0f
            Difficulty.EXTREME -> 3.0f
        }

        return (baseReward * difficultyMultiplier).toInt()
    }
}

data class VehicleStats(
    val maxSpeed: Float,
    val acceleration: Float,
    val handling: Float,
    val weight: Float,
    val fuelConsumption: Float
)

data class PartBoosts(
    val speedBoost: Float = 0f,
    val accelerationBoost: Float = 0f,
    val handlingBoost: Float = 0f,
    val totalWeight: Float = 0f
)

data class RaceSimulation(
    val timeSeconds: Float,
    val topSpeed: Float,
    val averageSpeed: Float
)
