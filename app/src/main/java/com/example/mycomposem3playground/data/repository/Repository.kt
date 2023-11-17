package com.example.mycomposem3playground.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mycomposem3playground.data.local.LocalDataSource
import com.example.mycomposem3playground.data.local.model.FavoritesItem
import com.example.mycomposem3playground.data.remote.AppService
import com.example.mycomposem3playground.data.remote.dtos.Movie
import com.example.mycomposem3playground.data.remote.dtos.ReviewsCatalog
import com.example.mycomposem3playground.data.remote.dtos.VideoCatalog
import com.example.mycomposem3playground.domain.pagination.MoviesPagingSource
import kotlinx.coroutines.flow.Flow

internal class Repository(private val appService: AppService, private val localDataSource: LocalDataSource): IRepository {

    override suspend fun getMoviesCatalog(numPage: Int?): List<Movie> {
        return appService.getMovies(numPage).body()?.results ?: emptyList()
    }

    override fun getMovies(selection: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MoviesPagingSource(appService, localDataSource, selection)
            }
        ).flow
    }

    override suspend fun getSingleMovie(id: Int): Movie {
        return appService.getSingleMovie(id).body()!!
    }

    override suspend fun getMovieVideos(id: Int): VideoCatalog {
        return appService.getVideos(id).body()!!
    }

    override suspend fun getMovieReviews(id: Int): ReviewsCatalog {
        return appService.getReviews(id).body()!!
    }

    override suspend fun updateFavorite(item: FavoritesItem, checkFav: Boolean) {
        if (checkFav) {
            localDataSource.storeFavoriteItem(item)
        } else {
            localDataSource.deleteFavoriteItem(item)
        }
    }
    override suspend fun getFavoriteStatus(movieId: Int): Boolean {
        return localDataSource.getFavoriteMovie(movieId)
    }
}