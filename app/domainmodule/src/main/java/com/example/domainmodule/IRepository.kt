package com.example.domainmodule

import androidx.paging.PagingData
import com.example.domainmodule.model.Movie
import com.example.domainmodule.model.ReviewsCatalog
import com.example.domainmodule.model.VideoCatalog
import com.example.domainmodule.model.FavoritesItem
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getMoviesCatalog(numPage:Int?): List<Movie>
    fun getMovies(selection: Int): Flow<PagingData<Movie>>
    suspend fun getSingleMovie(id: Int): Movie
    suspend fun getMovieVideos(id: Int): VideoCatalog
    suspend fun getMovieReviews(id: Int): ReviewsCatalog
    suspend fun updateFavorite(item: FavoritesItem, checkFav: Boolean)
    suspend fun getFavoriteStatus(movieId: Int): Boolean
}