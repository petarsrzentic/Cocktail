package com.example.cocktail.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.cocktail.R
import com.example.cocktail.adapters.PagerAdapter
import com.example.cocktail.data.database.entities.FavoritesEntity
import com.example.cocktail.databinding.ActivityDetailsBinding
import com.example.cocktail.ui.fragments.overview.OverviewFragment
import com.example.cocktail.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var menuItem: MenuItem

    private var cocktailSaved = false
    private var savedCocktailId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        // here you can add more fragments

        val titles = ArrayList<String>()
        titles.add("Overview")
        // here you can add titles for another fragments

        val resultBundle = Bundle()
        resultBundle.putParcelable("drinkBundle", args.result)

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )
        binding.viewPager2.isUserInputEnabled = false
        binding.viewPager2.apply {
            adapter = pagerAdapter
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        menuItem = menu.findItem(R.id.save_to_favorites_menu)
        checkSavedCocktails(menuItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            // save cocktail and change color of icon
        } else if (item.itemId == R.id.save_to_favorites_menu && !cocktailSaved) {
            saveToFavorites(item)
            Log.d("DetailsActivity", "if ${item.itemId} == ${R.id.save_to_favorites_menu} && ${!cocktailSaved}")
            // remove cocktail from favorites and change color of icon
        } else if (item.itemId == R.id.save_to_favorites_menu && cocktailSaved) {
            Log.d("DetailsActivity", "else if ${item.itemId} == ${R.id.save_to_favorites_menu} && ${cocktailSaved}")
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkSavedCocktails(menuItem: MenuItem) {
        mainViewModel.readFavoriteCocktail.observe(this) { favoriteEntity ->
            try {
                for (savedCocktail in favoriteEntity) {
                    if (savedCocktail.drink.idDrink == args.result.idDrink) {
                        changeMenuItemColor(menuItem, R.color.green)
                        savedCocktailId = savedCocktail.id
                        cocktailSaved = true
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailsActivity", e.message.toString())
            }
        }
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(savedCocktailId, args.result)
        mainViewModel.insertFavoriteCocktail(favoritesEntity)
        changeMenuItemColor(item, R.color.green)
        showSnackBar(getString(R.string.cocktail_saved))
        cocktailSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity = FavoritesEntity(savedCocktailId, args.result)
        mainViewModel.deleteFavoriteCocktail(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar(getString(R.string.removed_from_favorites))
        cocktailSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }

    override fun onDestroy() {
        super.onDestroy()
        changeMenuItemColor(menuItem, R.color.white)
    }
}