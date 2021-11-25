package com.example.cocktail.di

import android.content.Context
import androidx.room.Room
import com.example.cocktail.data.database.CocktailDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CocktailDatabase::class.java,
        "cocktails_table"
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: CocktailDatabase) = database.cocktailDao()

}