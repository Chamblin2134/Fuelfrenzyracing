package com.fuelfrenzyracing.game.data.models

data class RaceTrack(
    val id: String,
    val name: String,
    val distance: Float = 5280f,
    val difficulty: Difficulty,
    val curves: List<TrackCurve> = emptyList(),
    val weatherCondition: WeatherCondition = WeatherCondition.CLEAR,
    val rewardCoins: Int = 0
)

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD,
    EXTREME
}

enum class WeatherCondition {
    CLEAR,
    RAIN,
    FOG,
    STORMY
}

data class TrackCurve(
    val position: Float,
    val curveAngle: Float,
    val severity: Float
)

data class RaceResult(
    val vehicleId: String,
    val trackId: String,
    val timeSeconds: Float,
    val topSpeed: Float,
    val averageSpeed: Float,
    val coinsEarned: Int,
    val position: Int,
    val isNewRecord: Boolean = false
)
