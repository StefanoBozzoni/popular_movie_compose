package com.example.mycomposem3playground.domain.interactors

import androidx.compose.runtime.mutableStateOf
import androidx.paging.PagingData
import com.example.mycomposem3playground.data.remote.dtos.Movie
import com.example.mycomposem3playground.data.remote.dtos.MoviesCatalogDto
import com.example.mycomposem3playground.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetMoviesUC(private val remoteRepository: IRepository) {
    fun execute(params: Params): Flow<PagingData<Movie>> {
        return remoteRepository.getMovies(selection = params.selection)
    }

    class Params(val numPage: Int? = null, val selection: Int)
    /*
    suspend fun execute(params: Params? = null): List<Movie> {
        return remoteRepository.getMoviesCatalog(params?.numPage)
    }
    class Params(val numPage: Int? = null)
     */
}