package com.example.cocktail.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.cocktail.data.Repository
import com.example.cocktail.data.database.entities.CocktailEntity
import com.example.cocktail.data.database.entities.FavoritesEntity
import com.example.cocktail.models.Cocktails
import com.example.cocktail.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /* Room Database*/
    var readCocktails: LiveData<List<CocktailEntity>> =
        repository.local.readCocktails().asLiveData()
    var readFavoriteCocktail: LiveData<List<FavoritesEntity>> =
        repository.local.readFavoriteCocktail().asLiveData()

    private fun insertCocktails(cocktailEntity: CocktailEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertCocktail(cocktailEntity)
        }

    fun insertFavoriteCocktail(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteCocktail(favoritesEntity)
        }

    fun deleteFavoriteCocktail(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteCocktail(favoritesEntity)
        }

    private fun deleteAllFavoriteCocktail() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavoriteCocktail()
        }

    /* Retrofit */
    var cocktailResponse: MutableLiveData<NetworkResult<Cocktails>> = MutableLiveData()
//    val searchCocktailResponse: MutableLiveData<NetworkResult<Cocktails>> = MutableLiveData()

    fun getCocktails(queries: Map<String, String>) = viewModelScope.launch {
        getCocktailsSafeCall(queries)
    }
//
//    fun getCocktailByName(searchQuery: Map<String, String>) = viewModelScope.launch {
//        searchCocktailSafeCall(searchQuery)
//    }


    private suspend fun getCocktailsSafeCall(queries: Map<String, String>) {
        cocktailResponse.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getSearchCocktails(queries)
                cocktailResponse.value = handleCocktailResponse(response)

                /*Place for response*/

                val cocktails = cocktailResponse.value!!.data
                if (cocktails != null) {
                    offlineCashCocktails(cocktails)
                }

            } catch (e: Exception) {
                cocktailResponse.value = NetworkResult.Error("Cocktails not found!")
            }
        } else {
            cocktailResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }
//
//    private suspend fun searchCocktailSafeCall(searchQuery: Map<String, String>) {
//        searchCocktailResponse.value = NetworkResult.Loading()
//
//        if (hasInternetConnection()) {
//            try {
//                val response = repository.remote.getCocktailByName(searchQuery)
//                searchCocktailResponse.value = handleCocktailResponse(response)
//
//                /*Place for response*/
//
//            } catch (e: Exception) {
//                searchCocktailResponse.value = NetworkResult.Error("Cocktails not found!")
//            }
//        } else {
//            searchCocktailResponse.value = NetworkResult.Error("No Internet Connection")
//        }
//    }

    private fun offlineCashCocktails(cocktails: Cocktails) {
        val cocktailEntity = CocktailEntity(cocktails)
        insertCocktails(cocktailEntity)
    }

    // Requesting and handling data from Api
    private fun handleCocktailResponse(response: Response<Cocktails>): NetworkResult<Cocktails> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout ")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.drinks.isNullOrEmpty() -> {
                return NetworkResult.Error("Cocktails not found!")
            }
            response.isSuccessful -> {
                val cocktails = response.body()
                return NetworkResult.Success(cocktails!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    //check internet connection
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}