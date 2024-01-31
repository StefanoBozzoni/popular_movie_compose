package com.example.domainmodule.interactors

import androidx.paging.PagingData
import com.example.domainmodule.IRepository
import com.example.domainmodule.model.Movie
import kotlinx.coroutines.flow.Flow

class GetMoviesUC(private val repository: IRepository) {
    fun execute(params: Params): Flow<PagingData<Movie>> {
        return repository.getMovies(selection = params.selection)
    }

    class Params(val selection: Int)
}