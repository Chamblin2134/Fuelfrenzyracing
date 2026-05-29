package com.fuelfrenzyracing.game.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        VehicleEntity::class,
        PartEntity::class,
        FuelIngredientEntity::class,
        RaceResultEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GameDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun partDao(): PartDao
    abstract fun fuelIngredientDao(): FuelIngredientDao
    abstract fun raceResultDao(): RaceResultDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getInstance(context: Context): GameDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "fuel_frenzy_racing.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
