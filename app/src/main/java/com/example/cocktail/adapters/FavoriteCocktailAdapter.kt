package com.example.cocktail.adapters

import android.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktail.R
import com.example.cocktail.data.database.entities.FavoritesEntity
import com.example.cocktail.databinding.FavoriteCocktailRowLayoutBinding
import com.example.cocktail.ui.fragments.favorites.FavoriteFragmentDirections
import com.example.cocktail.util.CocktailDiffUtil
import com.example.cocktail.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar

// To be able to use ContextualActionMenu we need to inherit from ActionMode.Callback
class FavoriteCocktailAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<FavoriteCocktailAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiSelection = false

    //this variable use to set title in contextualActionMode
    private lateinit var mActionMode: ActionMode

    private lateinit var rootView: View
    private var selectedCocktails = arrayListOf<FavoritesEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()
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
                val binding =
                    FavoriteCocktailRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)
        rootView = holder.itemView.rootView

        val currentCocktail = favoriteCocktails[position]
        holder.bind(currentCocktail)

        saveItemStateOnScroll(currentCocktail, holder)

        /* Single click listener */
        holder.itemView.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, currentCocktail)
            } else {
                val action =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetails(currentCocktail.drink)
                holder.itemView.findNavController().navigate(action)
            }
        }

        /* Long click listener */
        holder.itemView.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentCocktail)
                true
            } else {
                applySelection(holder, currentCocktail)
                true
            }

        }
    }

    private fun saveItemStateOnScroll(currentCocktail: FavoritesEntity, holder: MyViewHolder) {
        if (selectedCocktails.contains(currentCocktail)) {
            changeCocktailStyle(holder, R.color.cardBackgroundLightColor)
        } else {
            changeCocktailStyle(holder, R.color.cardBackgroundColor)
        }
    }

    private fun applySelection(holder: MyViewHolder, currentCocktail: FavoritesEntity) {
        if (selectedCocktails.contains(currentCocktail)) {
            selectedCocktails.remove(currentCocktail)
            changeCocktailStyle(holder, R.color.cardBackgroundColor)
            applyActionModeTitle()
        } else {
            selectedCocktails.add(currentCocktail)
            changeCocktailStyle(holder, R.color.cardBackgroundColor)
            applyActionModeTitle()
        }
    }

    private fun changeCocktailStyle(holder: MyViewHolder, backgroundColor: Int) {
        holder.itemView.findViewById<TextView>(R.id.favoriteCocktail_textView)
            .setTextColor(ContextCompat.getColor(requireActivity, backgroundColor))
    }

    private fun applyActionModeTitle() {
        when (selectedCocktails.size) {
            0 -> {
                mActionMode.finish()
                multiSelection = false
            }
            1 -> {
                mActionMode.title = "${selectedCocktails.size} cocktail selected"
            }
            else -> {
                mActionMode.title = "${selectedCocktails.size} cocktails selected"
            }
        }
    }

    override fun getItemCount(): Int {
        return favoriteCocktails.size
    }


    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorite_contextual_menu, menu)
        mActionMode = actionMode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_favorite_cocktail_menu) {
            selectedCocktails.forEach {
                mainViewModel.deleteFavoriteCocktail(it)
            }
            showSnackBar("${selectedCocktails.size} cocktail/s removed.")
            multiSelection = false
            selectedCocktails.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        myViewHolders.forEach { holder ->
            changeCocktailStyle(holder, R.color.primaryTextColor)
        }
        multiSelection = false
        selectedCocktails.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    fun setData(newFavoriteCocktails: List<FavoritesEntity>) {
        val favoriteCocktailDiffUtil = CocktailDiffUtil(favoriteCocktails, newFavoriteCocktails)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteCocktailDiffUtil)
        favoriteCocktails = newFavoriteCocktails
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    fun closeContextualActionMode() {
        if (this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }
}