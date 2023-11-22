package com.example.datamodule.data.local

import com.example.datamodule.data.local.database.AppDatabase
import com.example.datamodule.data.local.model.FavoritesItem

class LocalDataSource(private val database: AppDatabase) {

    suspend fun storeFavoriteItem(favoritesItem: FavoritesItem) {
        database.favoritesDao().insertFavorite(favoritesItem)
    }

    suspend fun deleteFavoriteItem(favoritesItem: FavoritesItem) {
        database.favoritesDao().deleteFavorite(favoritesItem)
    }

    suspend fun getFavoriteMovies(): List<FavoritesItem>? {
        return database.favoritesDao().getFavoriteMovieList()
    }

    suspend fun getFavoriteMovie(id: Int): Boolean {
        return (database.favoritesDao().getFavoriteMovie(id) != null)
    }

}