package com.example.cocktail.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktail(cocktailEntity: CocktailEntity)

    @Query("SELECT * FROM cocktails_table ORDER BY id ASC")
    fun readCocktails(): Flow<List<CocktailEntity>>
}