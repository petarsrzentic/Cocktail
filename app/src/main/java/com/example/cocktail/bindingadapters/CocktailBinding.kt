package com.example.cocktail.bindingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.cocktail.data.database.entities.CocktailEntity
import com.example.cocktail.models.Cocktails
import com.example.cocktail.models.Drink
import com.example.cocktail.ui.fragments.cocktail.CocktailFragmentDirections
import com.example.cocktail.util.NetworkResult

class CocktailBinding {

    companion object {

        @BindingAdapter("onCocktailClickListener")
        @JvmStatic
        fun onCocktailClickListener(cocktailRawLayout: ConstraintLayout, result: Drink) {
            cocktailRawLayout.setOnClickListener {
                try {
                    val action = 
                         CocktailFragmentDirections.actionCocktailFragmentToDetails(result)
                    cocktailRawLayout.findNavController().navigate(action)
                }catch (e: Exception) {
                    Log.d("onCocktailClickListener", e.toString())
                }
            }
        }

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun handleReadDataErrors(
            view: View,
            apiResponse: NetworkResult<Cocktails>?,
            database: List<CocktailEntity>?
        ) {
            when(view) {
                is ImageView -> {
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                }
                is TextView -> {
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                    view.text = apiResponse?.message.toString()
                }
            }
        }

    }
}