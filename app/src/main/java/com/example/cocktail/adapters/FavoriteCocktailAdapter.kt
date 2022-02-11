package com.example.cocktail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktail.data.database.entities.FavoritesEntity
import com.example.cocktail.databinding.FavoriteCocktailRowLayoutBinding
import com.example.cocktail.util.CocktailDiffUtil

class FavoriteCocktailAdapter : RecyclerView.Adapter<FavoriteCocktailAdapter.MyViewHolder>() {

    private var favoriteCocktails = emptyList<FavoritesEntity>()

    class MyViewHolder(private val binding: FavoriteCocktailRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(favoritesEntity: FavoritesEntity) {
                binding.favoritesEntity = favoritesEntity
                binding.executePendingBindings()
            }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteCocktailRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val selectedCocktail = favoriteCocktails[position]
        holder.bind(selectedCocktail)
    }

    override fun getItemCount(): Int {
       return favoriteCocktails.size
    }

    fun setData(newFavoriteCocktails: List<FavoritesEntity>) {
        val favoriteCocktailDiffUtil = CocktailDiffUtil(favoriteCocktails, newFavoriteCocktails)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteCocktailDiffUtil)
        favoriteCocktails = newFavoriteCocktails
        diffUtilResult.dispatchUpdatesTo(this)
    }
}