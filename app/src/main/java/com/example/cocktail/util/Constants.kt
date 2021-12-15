package com.example.cocktail.util

class Constants {

    companion object {

        private const val API_KEY = "9973533"
        const val BASE_URL = "https://www.thecocktaildb.com/api/json/v2/$API_KEY/"

        // API Query keys

        const val API_GET_COCKTAIL_POPULAR = "/api/json/v2/$API_KEY/popular.php"
        const val API_GET_COCKTAIL_LATEST = "/api/json/v2/$API_KEY/latest.php"
        const val API_GET_COCKTAIL_RECENT = "/api/json/v2/$API_KEY/recent.php"
        const val API_GET_COCKTAIL_RANDOM = "/api/json/v2/$API_KEY/randomselection.php"

        const val QUERY_RECENT = "recent.php"
        const val QUERY_RANDOM = "randomselection.php"
        const val QUERY_LATEST = "latest.php"

        // Bottom sheet and preferences
        const val DEFAULT_COCKTAIL = "popular"
        const val PREFERENCE_NAME = "cocktail_preferences"
        const val PREFERENCES_COCKTAIL_TYPE = "cocktailType"
        const val PREFERENCES_COCKTAIL_TYPE_ID = "cocktailTypeId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"

    }
}
