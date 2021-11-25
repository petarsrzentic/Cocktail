package com.example.cocktail.data

import com.example.cocktail.data.database.CocktailDao
import com.example.cocktail.data.database.CocktailEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val cocktailDao: CocktailDao
) {

    fun readDatabase(): Flow<List<CocktailEntity>> {
        return cocktailDao.readCocktails()
    }

    suspend fun insertCocktail(cocktailEntity: CocktailEntity) {
        cocktailDao.insertCocktail(cocktailEntity)
    }
}