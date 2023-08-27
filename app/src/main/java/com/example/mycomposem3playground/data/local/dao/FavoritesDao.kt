package com.example.mycomposem3playground.data.local.dao

import androidx.room.*
import com.example.mycomposem3playground.data.local.database.DatabaseConstants
import com.example.mycomposem3playground.data.local.model.FavoritesItem

@Dao
abstract class FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertFavorite(cachedTest: FavoritesItem) : Long

    @Delete
    abstract suspend fun deleteFavorite(item: FavoritesItem) : Int

    @Query("SELECT count(*) FROM " + DatabaseConstants.TABLE_FAVORITES)
    abstract suspend fun countTests(): Int

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_FAVORITES}")
    abstract suspend fun getFavoriteMovieList(): List<FavoritesItem>?

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_FAVORITES} where id=:id")
    abstract suspend fun getFavoriteMovie(id:Int): FavoritesItem?

    @Query("DELETE FROM ${DatabaseConstants.TABLE_FAVORITES}")
    abstract suspend fun clearFavoritesTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllFavorites(cachedTests: List<FavoritesItem>) : List<Long>

}