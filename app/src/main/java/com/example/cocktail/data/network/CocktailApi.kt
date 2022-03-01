package com.example.cocktail.data.network

import com.example.cocktail.models.Cocktails
import com.example.cocktail.util.Constants.Companion.API_GET_COCKTAIL
import com.example.cocktail.util.Constants.Companion.API_SEARCH_COCKTAIL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface CocktailApi {

    @GET(API_GET_COCKTAIL)
    suspend fun getCocktails(
        @QueryMap queries: Map<String, String>
    ): Response<Cocktails>

    @GET(API_SEARCH_COCKTAIL)
    suspend fun searchCocktails(
        @QueryMap queries: Map<String, String>
    ): Response<Cocktails>

}