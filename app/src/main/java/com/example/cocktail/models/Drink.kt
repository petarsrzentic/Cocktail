package com.example.cocktail.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Drink(
    @SerializedName("idDrink")
    val idDrink: String,
    @SerializedName("strAlcoholic")
    val strAlcoholic: String,
    @SerializedName("strDrink")
    val strDrink: String,
    @SerializedName("strDrinkThumb")
    val strDrinkThumb: String,
    @SerializedName("strGlass")
    val strGlass: String,
    @SerializedName("strImageSource")
    val strImageSource: String,
    @SerializedName("strIngredient1")
    val strIngredient1: String,
    @SerializedName("strIngredient2")
    val strIngredient2: String,
    @SerializedName("strIngredient3")
    val strIngredient3: String,
    @SerializedName("strIngredient4")
    val strIngredient4: String? = null,
    @SerializedName("strIngredient5")
    val strIngredient5: String? = null,
    @SerializedName("strIngredient6")
    val strIngredient6: String? = null,
    @SerializedName("strIngredient7")
    val strIngredient7: String? = null,
    @SerializedName("strInstructions")
    val strInstructions: String,
    @SerializedName("strInstructionsDE")
    val strInstructionsDE: @RawValue Any,
    @SerializedName("strInstructionsIT")
    val strInstructionsIT: @RawValue Any,
    @SerializedName("strMeasure1")
    val strMeasure1: String,
    @SerializedName("strMeasure2")
    val strMeasure2: String,
    @SerializedName("strMeasure3")
    val strMeasure3: String,
    @SerializedName("strMeasure4")
    val strMeasure4: String? = null,
    @SerializedName("strMeasure5")
    val strMeasure5: String? = null,
    @SerializedName("strMeasure6")
    val strMeasure6: String? = null,
    @SerializedName("strMeasure7")
    val strMeasure7: String? = null,
    @SerializedName("strTags")
    val strTags: String,
) : Parcelable