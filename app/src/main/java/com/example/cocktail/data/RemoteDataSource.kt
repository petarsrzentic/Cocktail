package com.example.cocktail.data

import com.example.cocktail.data.network.CocktailApi
import com.example.cocktail.models.Cocktails
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val cocktailApi: CocktailApi
) {
    suspend fun getCocktailPopular(queries: Map<String, String>): Response<Cocktails> {
        return cocktailApi.getCocktailsPopular(queries)
    }

    suspend fun getCocktailLatest(queries: Map<String, String>): Response<Cocktails> {
        return cocktailApi.getCocktailsLatest(queries)
    }

    suspend fun getCocktailsRecent(queries: Map<String, String>): Response<Cocktails> {
        return cocktailApi.getCocktailsRecent(queries)
    }

    suspend fun getCocktailsRandom(queries: Map<String, String>): Response<Cocktails> {
        return cocktailApi.getCocktailsRandom(queries)
    }
}