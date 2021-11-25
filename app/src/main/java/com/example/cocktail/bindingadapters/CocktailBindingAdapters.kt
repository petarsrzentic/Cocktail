package com.example.cocktail.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.cocktail.R

class CocktailBindingAdapters {

    companion object {

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_no_drink)
            }
        }

    }

}