package com.example.cocktail.data.network

import com.example.cocktail.models.Cocktails
import com.example.cocktail.util.Constants.Companion.API_GET_COCKTAIL_LATEST
import com.example.cocktail.util.Constants.Companion.API_GET_COCKTAIL_POPULAR
import com.example.cocktail.util.Constants.Companion.API_GET_COCKTAIL_RANDOM
import com.example.cocktail.util.Constants.Companion.API_GET_COCKTAIL_RECENT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface CocktailApi {

        @GET(API_GET_COCKTAIL_POPULAR)
        suspend fun getCocktailsPopular(
            @QueryMap queries: Map<String, String>
        ): Response<Cocktails>

        // try another call
        @GET(API_GET_COCKTAIL_LATEST)
        suspend fun getCocktailsLatest(
            @QueryMap queries: Map<String, String>
        ): Response<Cocktails>


        @GET(API_GET_COCKTAIL_RECENT)
        suspend fun getCocktailsRecent(
            @QueryMap queries: Map<String, String>
        ): Response<Cocktails>

        @GET(API_GET_COCKTAIL_RANDOM)
        suspend fun getCocktailsRandom(
            @QueryMap queries: Map<String, String>
        ): Response<Cocktails>
}