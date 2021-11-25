package com.example.cocktail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktail.databinding.CocktailRowLayoutBinding
import com.example.cocktail.models.Cocktails
import com.example.cocktail.models.Drink
import com.example.cocktail.util.CocktailDiffUtil

class CocktailAdapter : RecyclerView.Adapter<CocktailAdapter.MyViewHolder>() {

    private var cocktails = emptyList<Drink>()

    class MyViewHolder(private val binding: CocktailRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //update layout when there is change
        fun bind(drink: Drink) {
            binding.drink = drink
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CocktailRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentCocktail = cocktails[position]
        holder.bind(currentCocktail)
    }

    override fun getItemCount(): Int {
        return cocktails.size
    }

    fun setData(newData: Cocktails) {
        val cocktailDiffUtil = CocktailDiffUtil(cocktails, newData.drinks)
        val diffUtilResult = DiffUtil.calculateDiff(cocktailDiffUtil)
        cocktails = newData.drinks
        diffUtilResult.dispatchUpdatesTo(this)
    }
}