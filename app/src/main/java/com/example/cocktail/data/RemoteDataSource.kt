package com.example.cocktail.data

import com.example.cocktail.data.network.CocktailApi
import com.example.cocktail.models.Cocktails
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val cocktailApi: CocktailApi
) {
    suspend fun getSearchCocktails(queries: Map<String, String>): Response<Cocktails> {
        return cocktailApi.getCocktails(queries)
    }

    suspend fun searchCocktails(searchQuery: Map<String, String>): Response<Cocktails> {
        return cocktailApi.searchCocktails(searchQuery)
    }

}