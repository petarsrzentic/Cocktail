package com.example.cocktail.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cocktail.models.Drink
import com.example.cocktail.util.Constants.Companion.FAVORITE_COCKTAIL_TABLE

@Entity(tableName = FAVORITE_COCKTAIL_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var drink: Drink
)