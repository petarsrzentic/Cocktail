package com.example.cocktail.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktail.data.DataStoreRepository
import com.example.cocktail.util.Constants.Companion.DEFAULT_COCKTAIL
import com.example.cocktail.util.Constants.Companion.QUERY_LATEST
import com.example.cocktail.util.Constants.Companion.QUERY_RANDOM
import com.example.cocktail.util.Constants.Companion.QUERY_RECENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var cocktailType = DEFAULT_COCKTAIL

    val readCocktailType = dataStoreRepository.readCocktailType

    fun saveCocktailType(cocktailType: String, cocktailTypeId: Int) =
        viewModelScope.launch {
            dataStoreRepository.saveCocktailType(cocktailType, cocktailTypeId)
        }

    fun applyQueries(): HashMap<String, String> {

        viewModelScope.launch {
            readCocktailType.collect { value ->
                cocktailType = value.selectedCocktail
            }
        }

        val queries: HashMap<String, String> = HashMap()
        queries[DEFAULT_COCKTAIL] = cocktailType//"popular"
        queries[QUERY_LATEST] = "latest"
        queries[QUERY_RECENT] = "recent"
        queries[QUERY_RANDOM] = "random"

        return queries

    }
}