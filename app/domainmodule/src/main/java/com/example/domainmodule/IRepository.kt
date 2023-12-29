package com.example.domainmodule

import com.example.domainmodule.model.FavoritesItem
import com.example.domainmodule.model.IDataProvider
import com.example.domainmodule.model.Movie
import com.example.domainmodule.model.ReviewsCatalog
import com.example.domainmodule.model.VideoCatalog

interface IRepository {
    suspend fun getMoviesCatalog(numPage:Int?): List<Movie>
    fun <T:Any> getMovies(selection: Int): IDataProvider<T>
    suspend fun getSingleMovie(id: Int): Movie
    suspend fun getMovieVideos(id: Int): VideoCatalog
    suspend fun getMovieReviews(id: Int): ReviewsCatalog
    suspend fun updateFavorite(item: FavoritesItem, checkFav: Boolean)
    suspend fun getFavoriteStatus(movieId: Int): Boolean
}