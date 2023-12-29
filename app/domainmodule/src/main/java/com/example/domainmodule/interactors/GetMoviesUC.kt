package com.example.domainmodule.interactors

import com.example.domainmodule.IRepository
import com.example.domainmodule.model.IDataProvider

class GetMoviesUC(private val repository: IRepository) {
    fun <T : Any> execute(params: Params): IDataProvider<T> {
        return repository.getMovies(selection = params.selection)
    }

    class Params(val selection: Int)
}