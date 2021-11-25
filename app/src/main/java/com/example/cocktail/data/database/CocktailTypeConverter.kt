package com.example.cocktail.data.database

import androidx.room.TypeConverter
import com.example.cocktail.models.Cocktails
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
        return gson.fromJson(data,listType)
    }

}