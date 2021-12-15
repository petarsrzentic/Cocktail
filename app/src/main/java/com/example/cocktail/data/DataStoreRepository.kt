package com.example.cocktail.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.cocktail.util.Constants.Companion.DEFAULT_COCKTAIL
import com.example.cocktail.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.example.cocktail.util.Constants.Companion.PREFERENCES_COCKTAIL_TYPE
import com.example.cocktail.util.Constants.Companion.PREFERENCES_COCKTAIL_TYPE_ID
import com.example.cocktail.util.Constants.Companion.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCE_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedCocktailType = stringPreferencesKey(PREFERENCES_COCKTAIL_TYPE)
        val selectedCocktailTypeId = intPreferencesKey(PREFERENCES_COCKTAIL_TYPE_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveCocktailType(cocktailType: String, cocktailTypeId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedCocktailType] = cocktailType
            preferences[PreferenceKeys.selectedCocktailTypeId] = cocktailTypeId
        }
    }

    suspend fun saveBackOnline(backOnline: Boolean) {
        dataStore.edit {preferences ->
            preferences[PreferenceKeys.backOnline] = backOnline
        }
    }

    val readCocktailType: Flow<CocktailType> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedCocktailType =
                preferences[PreferenceKeys.selectedCocktailType] ?: DEFAULT_COCKTAIL
            val selectedCocktailTypeId = preferences[PreferenceKeys.selectedCocktailTypeId] ?: 0
            CocktailType(
                selectedCocktailType,
                selectedCocktailTypeId
            )
        }

    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {preferences ->
            val backOnline =  preferences[PreferenceKeys.backOnline] ?: false
            backOnline
        }
}

data class CocktailType(
    val selectedCocktail: String,
    val selectedCocktailId: Int
)