package com.fuelfrenzyracing.game.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles")
    fun getAllVehicles(): Flow<List<VehicleEntity>>

    @Query("SELECT * FROM vehicles WHERE id = :id")
    suspend fun getVehicleById(id: String): VehicleEntity?

    @Insert
    suspend fun insert(vehicle: VehicleEntity)

    @Update
    suspend fun update(vehicle: VehicleEntity)

    @Delete
    suspend fun delete(vehicle: VehicleEntity)
}

@Dao
interface PartDao {
    @Query("SELECT * FROM parts")
    fun getAllParts(): Flow<List<PartEntity>>

    @Query("SELECT * FROM parts WHERE id = :id")
    suspend fun getPartById(id: String): PartEntity?

    @Insert
    suspend fun insert(part: PartEntity)

    @Update
    suspend fun update(part: PartEntity)
}

@Dao
interface FuelIngredientDao {
    @Query("SELECT * FROM fuel_ingredients")
    fun getAllIngredients(): Flow<List<FuelIngredientEntity>>

    @Query("SELECT * FROM fuel_ingredients WHERE category = :category")
    fun getIngredientsByCategory(category: String): Flow<List<FuelIngredientEntity>>

    @Insert
    suspend fun insert(ingredient: FuelIngredientEntity)
}

@Dao
interface RaceResultDao {
    @Query("SELECT * FROM race_results ORDER BY timestamp DESC")
    fun getAllResults(): Flow<List<RaceResultEntity>>

    @Query("SELECT * FROM race_results WHERE vehicleId = :vehicleId ORDER BY timeSeconds ASC LIMIT 1")
    suspend fun getBestTimeForVehicle(vehicleId: String): RaceResultEntity?

    @Insert
    suspend fun insert(result: RaceResultEntity)
}
