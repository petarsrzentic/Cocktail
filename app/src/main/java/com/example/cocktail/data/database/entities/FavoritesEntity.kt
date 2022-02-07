package com.example.cocktail.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cocktail.models.Drink

@Entity(tableName = "favorite_cocktail_table")
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var drink: Drink
)