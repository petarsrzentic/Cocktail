package com.example.cocktail.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CocktailEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(CocktailTypeConverter::class)
abstract class CocktailDatabase: RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
}