package com.example.mycomposem3playground.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mycomposem3playground.data.local.model.FavoritesItem
import com.example.mycomposem3playground.data.remote.dtos.Movie
import com.example.mycomposem3playground.domain.interactors.GetMoviesUC
import com.example.mycomposem3playground.domain.interactors.GetSingleMovieUC
import com.example.mycomposem3playground.domain.interactors.UpdateFavorites
import com.example.mycomposem3playground.domain.model.MovieDetailInfo
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

    private val _moviesList = MutableStateFlow(PagingData.empty<Movie>())
    val moviesList: StateFlow<PagingData<Movie>> = _moviesList

    private val _movie = MutableStateFlow<MovieDetailInfo?>(null)
    val movie: StateFlow<MovieDetailInfo?> = _movie

    var selection: Int = 0
        private set

    init {
        getMovies(selection)
    }

    fun getMovies(selection: Int) {
        viewModelScope.launch {
            pageFlow = getMoviesUseCase.execute(GetMoviesUC.Params(selection = selection)).cachedIn(this)
            pageFlow.collect { _moviesList.value = it }
        }
    }

    fun resetMovieList() {
        viewModelScope.launch {
            MutableStateFlow<PagingData<Movie>>(value = PagingData.empty()).collect { _moviesList.value = it }
        }
    }

    //anche questo funziona e non coinvolge l'uso di un altro MutableStateFlow
    fun getMovies2(selection: Int): Flow<PagingData<Movie>> {  //ritorna un flow
        return getMoviesUseCase.execute(GetMoviesUC.Params(selection = selection)).cachedIn(viewModelScope)
    }

    /*
    fun getSingleMovie(id: Int) {
        viewModelScope.launch {
            _movie.value = getSingleMovieUseCase.execute(GetSingleMovieUC.Params(id))
        }
    }

     */

    suspend fun suspendGetSingleMovie(id: Int): MovieDetailInfo {
       return getSingleMovieUseCase.execute(GetSingleMovieUC.Params(id))
    }

    fun UpdateFavMovie(item: FavoritesItem, favChecked: Boolean) {
        viewModelScope.launch {
            updateFavMovieUC.execute(UpdateFavorites.Params(item, favChecked))
        }
    }
}