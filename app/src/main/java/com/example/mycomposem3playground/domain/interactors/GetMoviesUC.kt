package com.example.mycomposem3playground.domain.interactors

import androidx.paging.PagingData
import com.example.mycomposem3playground.data.remote.dtos.Movie
import com.example.mycomposem3playground.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUC @Inject constructor(private val remoteRepository: IRepository) {
    fun execute(params: Params): Flow<PagingData<Movie>> {
        return remoteRepository.getMovies(selection = params.selection)
    }

    class Params(val numPage: Int? = null, val selection: Int)
}