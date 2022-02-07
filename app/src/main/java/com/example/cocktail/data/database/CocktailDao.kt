package com.example.cocktail.data.database

import androidx.room.*
import com.example.cocktail.data.database.entities.CocktailEntity
import com.example.cocktail.data.database.entities.FavoritesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktail(cocktailEntity: CocktailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCocktail(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM cocktails_table ORDER BY id ASC")
    fun readCocktails(): Flow<List<CocktailEntity>>

    @Query("SELECT * FROM favorite_cocktail_table ORDER BY id ASC")
    fun readFavoriteCocktails(): Flow<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavoriteCocktail(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorite_cocktail_table")
    suspend fun deleteAllFavoriteCocktail()
}