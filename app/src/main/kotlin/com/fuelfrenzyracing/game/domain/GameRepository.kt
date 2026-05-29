package com.fuelfrenzyracing.game.domain

import com.fuelfrenzyracing.game.data.database.GameDatabase
import com.fuelfrenzyracing.game.data.database.PartEntity
import com.fuelfrenzyracing.game.data.database.RaceResultEntity
import com.fuelfrenzyracing.game.data.database.VehicleEntity
import com.fuelfrenzyracing.game.data.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepository(private val database: GameDatabase) {

    fun getAllVehicles(): Flow<List<VehicleEntity>> {
        return database.vehicleDao().getAllVehicles()
    }

    suspend fun getVehicleById(id: String): VehicleEntity? {
        return database.vehicleDao().getVehicleById(id)
    }

    suspend fun updateVehicle(vehicle: VehicleEntity) {
        database.vehicleDao().update(vehicle)
    }

    fun getAllParts(): Flow<List<Part>> {
        return database.partDao().getAllParts().map { partEntities ->
            partEntities.map { it.toPart() }
        }
    }

    fun getAllIngredients(): Flow<List<FuelIngredient>> {
        return database.fuelIngredientDao().getAllIngredients().map { ingredients ->
            ingredients.map { it.toFuelIngredient() }
        }
    }

    suspend fun saveRaceResult(result: RaceResult) {
        database.raceResultDao().insert(result.toEntity())
    }

    fun getAllRaceResults(): Flow<List<RaceResult>> {
        return database.raceResultDao().getAllResults().map { results ->
            results.map { it.toRaceResult() }
        }
    }

    suspend fun getBestTimeForVehicle(vehicleId: String): Float? {
        return database.raceResultDao().getBestTimeForVehicle(vehicleId)?.timeSeconds
    }

    private fun PartEntity.toPart(): Part {
        return Part(
            id = this.id,
            name = this.name,
            type = PartType.valueOf(this.type),
            speedBoost = this.speedBoost,
            accelerationBoost = this.accelerationBoost,
            handlingBoost = this.handlingBoost,
            weight = this.weight,
            cost = this.cost
        )
    }

    private fun com.fuelfrenzyracing.game.data.database.FuelIngredientEntity.toFuelIngredient(): FuelIngredient {
        return FuelIngredient(
            id = this.id,
            name = this.name,
            category = IngredientCategory.valueOf(this.category),
            speedBoost = this.speedBoost,
            accelerationBoost = this.accelerationBoost,
            fuelConsumption = this.fuelConsumption,
            weight = this.weight,
            description = this.description
        )
    }

    private fun RaceResult.toEntity(): RaceResultEntity {
        return RaceResultEntity(
            vehicleId = this.vehicleId,
            trackId = this.trackId,
            timeSeconds = this.timeSeconds,
            topSpeed = this.topSpeed,
            averageSpeed = this.averageSpeed,
            coinsEarned = this.coinsEarned,
            position = this.position,
            isNewRecord = this.isNewRecord
        )
    }

    private fun RaceResultEntity.toRaceResult(): RaceResult {
        return RaceResult(
            vehicleId = this.vehicleId,
            trackId = this.trackId,
            timeSeconds = this.timeSeconds,
            topSpeed = this.topSpeed,
            averageSpeed = this.averageSpeed,
            coinsEarned = this.coinsEarned,
            position = this.position,
            isNewRecord = this.isNewRecord
        )
    }
}
