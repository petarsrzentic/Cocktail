package com.example.cocktail.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocktail.data.DataStoreRepository
import com.example.cocktail.util.Constants.Companion.DEFAULT_COCKTAIL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var cocktailType = DEFAULT_COCKTAIL

    var networkStatus = false
    var backOnline = false

    private val readCocktailType = dataStoreRepository.readCocktailType
    var readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    fun saveCocktailType(cocktailType: String, cocktailTypeId: Int) =
        viewModelScope.launch {
            dataStoreRepository.saveCocktailType(cocktailType, cocktailTypeId)
        }

    fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    fun applyQueries(): HashMap<String, String> {

        viewModelScope.launch {
            readCocktailType.collect { value ->
                cocktailType = value.selectedCocktail
            }
        }

        val queries: HashMap<String, String> = HashMap()
        queries[DEFAULT_COCKTAIL] = cocktailType//"search"

        return queries

    }

//    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
//        val queries: HashMap<String, String> = HashMap()
//    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(), "We're back online", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }
}