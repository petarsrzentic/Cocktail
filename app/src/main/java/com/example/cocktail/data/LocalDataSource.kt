package com.example.cocktail.data

import com.example.cocktail.data.database.CocktailDao
import com.example.cocktail.data.database.entities.CocktailEntity
import com.example.cocktail.data.database.entities.FavoritesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val cocktailDao: CocktailDao
) {

    fun readCocktails(): Flow<List<CocktailEntity>> {
        return cocktailDao.readCocktails()
    }

    fun readFavoriteCocktail(): Flow<List<FavoritesEntity>> {
        return cocktailDao.readFavoriteCocktails()
    }

    suspend fun insertCocktail(cocktailEntity: CocktailEntity) {
        cocktailDao.insertCocktail(cocktailEntity)
    }

    suspend fun insertFavoriteCocktail(favoritesEntity: FavoritesEntity) {
        cocktailDao.insertFavoriteCocktail(favoritesEntity)
    }

    suspend fun deleteFavoriteCocktail(favoritesEntity: FavoritesEntity) {
        cocktailDao.deleteFavoriteCocktail(favoritesEntity)
    }

    suspend fun deleteAllFavoriteCocktail() {
        cocktailDao.deleteAllFavoriteCocktail()
    }
}