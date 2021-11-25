package com.example.cocktail.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cocktail.models.Cocktails

@Entity(tableName = "cocktails_table")
class CocktailEntity(
    var cocktails : Cocktails
) {
    @PrimaryKey(autoGenerate = false)
    var id : Int = 0
}