package com.example.cocktail.util

class Constants {

    companion object {

        private const val API_KEY = "9973533"
        const val BASE_URL = "https://www.thecocktaildb.com"

        // API Query keys

        const val API_GET_COCKTAIL = "/api/json/v2/$API_KEY/search.php?s"
        const val API_SEARCH_COCKTAIL = "/api/json/v2/$API_KEY/search.php"
        const val FILTER_COCKTAIL_BY_ALC = "/api/json/v2/$API_KEY/filter.php"
        const val FILTER_COCKTAIL_POPULAR = "/api/json/v2/$API_KEY/popular.php"
        const val FILTER_COCKTAIL_LATEST = "/api/json/v2/$API_KEY/latest.php"

        // Bottom sheet and preferences
        const val DEFAULT_COCKTAIL = "search"
        const val POPULAR_COCKTAIL = "popular"
        const val LATEST_COCKTAIL = "latest"
        const val PREFERENCE_NAME = "cocktail_preferences"
        const val PREFERENCES_COCKTAIL_TYPE = "cocktailType"
        const val PREFERENCES_COCKTAIL_TYPE_ID = "cocktailTypeId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"

        //Room database
        const val DATABASE_NAME = "cocktail_database"
        const val COCKTAILS_TABLE = "cocktails_table"
        const val FAVORITE_COCKTAIL_TABLE = "favorite_cocktail_table"
        const val PARCELABLE_KEY = "drinkBundle"

    }
}
