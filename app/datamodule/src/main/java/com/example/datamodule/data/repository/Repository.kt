package com.example.datamodule.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.datamodule.data.local.LocalDataSource
import com.example.datamodule.data.local.model.FavoritesItem
import com.example.datamodule.data.remote.AppService
import com.example.domainmodule.IRepository
import com.example.domainmodule.dtos.Movie
import com.example.domainmodule.dtos.ReviewsCatalog
import com.example.domainmodule.dtos.VideoCatalog
import com.example.datamodule.data.pagination.MoviesPagingSource
import com.example.datamodule.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class Repository(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource): IRepository {

    override suspend fun getMoviesCatalog(numPage: Int?): List<Movie> {
        return remoteDataSource.appService.getMovies(numPage).body()?.results ?: emptyList()
    }

    override fun getMovies(selection: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                MoviesPagingSource(remoteDataSource.appService, localDataSource, selection)
            }
        ).flow
    }

    override suspend fun getSingleMovie(id: Int): Movie {
        return remoteDataSource.appService.getSingleMovie(id).body()!!
    }

    override suspend fun getMovieVideos(id: Int): VideoCatalog {
        return remoteDataSource.appService.getVideos(id).body()!!
    }

    override suspend fun getMovieReviews(id: Int): ReviewsCatalog {
        return remoteDataSource.appService.getReviews(id).body()!!
    }

    override suspend fun updateFavorite(item: com.example.domainmodule.model.FavoritesItem, checkFav: Boolean) {
        if (checkFav) {
            localDataSource.storeFavoriteItem(item.toData())
        } else {
            localDataSource.deleteFavoriteItem(item.toData())
        }
    }

    override suspend fun getFavoriteStatus(movieId: Int): Boolean {
        return localDataSource.getFavoriteMovie(movieId)
    }
}

fun com.example.domainmodule.model.FavoritesItem.toData(): FavoritesItem = FavoritesItem(this.id, this.posterPath)