package com.example.cocktail.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cocktails(
    @SerializedName("drinks")
    val drinks: List<Drink>
) : Parcelable