package com.example.cocktail.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cocktail.models.Cocktails
import com.example.cocktail.util.Constants.Companion.COCKTAILS_TABLE

@Entity(tableName = COCKTAILS_TABLE)
class CocktailEntity(
    var cocktails : Cocktails
) {
    @PrimaryKey(autoGenerate = false)
    var id : Int = 0
}