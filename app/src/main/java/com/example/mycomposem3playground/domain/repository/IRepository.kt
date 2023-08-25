package com.example.mycomposem3playground.domain.repository

import androidx.paging.PagingData
import com.example.mycomposem3playground.data.local.model.FavoritesItem
import com.example.mycomposem3playground.data.remote.dtos.Movie
import com.example.mycomposem3playground.data.remote.dtos.MoviesCatalogDto
import com.example.mycomposem3playground.data.remote.dtos.ReviewsCatalog
import com.example.mycomposem3playground.data.remote.dtos.VideoCatalog
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getMoviesCatalog(numPage:Int?): List<Movie>
    fun getMovies(selection: Int): Flow<PagingData<Movie>>
    suspend fun getSingleMovie(id: Int): Movie
    suspend fun getMovieVideos(id: Int): VideoCatalog
    suspend fun getMovieReviews(id: Int): ReviewsCatalog
    suspend fun updateFavorite(item: FavoritesItem, checkFav: Boolean)
}