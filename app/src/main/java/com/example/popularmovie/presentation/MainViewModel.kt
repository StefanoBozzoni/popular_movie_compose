package com.example.popularmovie.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domainmodule.interactors.GetMoviesUC
import com.example.domainmodule.interactors.GetSingleMovieUC
import com.example.domainmodule.interactors.UpdateFavorites
import com.example.domainmodule.model.FavoritesItem
import com.example.domainmodule.model.Movie
import com.example.domainmodule.model.MovieDetailInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class MainViewModel(
    val getMoviesUseCase: GetMoviesUC,
    val getSingleMovieUseCase: GetSingleMovieUC,
    val updateFavMovieUC: UpdateFavorites,

    ): ViewModel() {
    var pageFlow by mutableStateOf<Flow<PagingData<Movie>>>(flowOf())
        private set

    private var _moviesList = MutableStateFlow(PagingData.empty<Movie>())
    val moviesList: StateFlow<PagingData<Movie>> get() = _moviesList

    private val _movie = MutableStateFlow<MovieDetailInfo?>(null)
    val movie: StateFlow<MovieDetailInfo?> = _movie

    var selection: Int = 0
        private set

    init {
        getMovies(selection)
    }

    fun resetFlow() {
        _moviesList =  MutableStateFlow(PagingData.empty<Movie>())
    }

    fun getMovies(selection: Int) {
        viewModelScope.launch {
            pageFlow = getMoviesUseCase
                .execute<Flow<PagingData<Movie>>>(GetMoviesUC.Params(selection = selection))
                .getMyData()
                .cachedIn(this)
            pageFlow.collect { _moviesList.value = it }
        }
    }

    //this also works and doesn't need another flow
    fun getMovies2(selection: Int): Flow<PagingData<Movie>> {  //returns a flow
        return getMoviesUseCase
            .execute<Flow<PagingData<Movie>>>(GetMoviesUC.Params(selection = selection))
            .getMyData()
            .cachedIn(viewModelScope)
    }

    suspend fun suspendGetSingleMovie(id: Int): MovieDetailInfo {
       return getSingleMovieUseCase.execute(GetSingleMovieUC.Params(id))
    }

    fun UpdateFavMovie(item: FavoritesItem, favChecked: Boolean) {
        viewModelScope.launch {
            updateFavMovieUC.execute(UpdateFavorites.Params(item, favChecked))
        }
    }
}