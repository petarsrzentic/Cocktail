package com.example.cocktail.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cocktail.data.database.entities.CocktailEntity
import com.example.cocktail.data.database.entities.FavoritesEntity

@Database(
    entities = [CocktailEntity::class, FavoritesEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(CocktailTypeConverter::class)
abstract class CocktailDatabase: RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
}