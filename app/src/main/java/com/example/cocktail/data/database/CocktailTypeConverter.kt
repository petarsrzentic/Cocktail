package com.example.cocktail.data.database

import androidx.room.TypeConverter
import com.example.cocktail.data.database.entities.FavoritesEntity
import com.example.cocktail.models.Cocktails
import com.example.cocktail.models.Drink
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CocktailTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun cocktailToString(cocktails: Cocktails): String {
        return gson.toJson(cocktails)
    }

    @TypeConverter
    fun stringToCocktail(data: String): Cocktails {
        val listType = object : TypeToken<Cocktails>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun drinkToString(drink: Drink): String {
        return gson.toJson(drink)
    }

    @TypeConverter
    fun stringToDrink(data: String): Drink {
        val listType = object : TypeToken<Drink>() {}.type
        return gson.fromJson(data, listType)
    }

}