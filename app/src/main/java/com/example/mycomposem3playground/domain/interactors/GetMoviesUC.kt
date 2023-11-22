package com.example.mycomposem3playground.domain.interactors

import androidx.paging.PagingData
import com.example.domainmodule.IRepository
import com.example.domainmodule.dtos.Movie
import kotlinx.coroutines.flow.Flow

class GetMoviesUC(private val remoteRepository: IRepository) {
    fun execute(params: Params): Flow<PagingData<Movie>> {
        return remoteRepository.getMovies(selection = params.selection)
    }

    class Params(val numPage: Int? = null, val selection: Int)
}