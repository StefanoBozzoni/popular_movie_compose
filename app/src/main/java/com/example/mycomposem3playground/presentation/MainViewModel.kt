package com.example.mycomposem3playground.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mycomposem3playground.data.remote.dtos.Movie
import com.example.mycomposem3playground.data.remote.dtos.MoviesCatalogDto
import com.example.mycomposem3playground.domain.interactors.GetMoviesUC
import com.example.mycomposem3playground.domain.interactors.GetSingleMovieUC
import com.example.mycomposem3playground.domain.model.MovieDetailInfo
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    val getMoviesUseCase: GetMoviesUC,
    val getSingleMovieUseCase: GetSingleMovieUC

): ViewModel() {

    private val _moviesList = MutableStateFlow(PagingData.empty<Movie>())
    val moviesList: StateFlow<PagingData<Movie>> = _moviesList

    private val _movie = MutableStateFlow<MovieDetailInfo?>(null)
    val movie: StateFlow<MovieDetailInfo?> = _movie


    /*
    init {
        getMovies()
    }
    */

    fun getMovies(selection: Int) {
        viewModelScope.launch {
            getMoviesUseCase.execute(GetMoviesUC.Params(selection = selection)).cachedIn(this).collect {_moviesList.value = it }
        }
    }

    //anche questo funziona e non coinvolge l'uso di un altro MutableStateFlow
    fun getMovies2(): Flow<PagingData<Movie>> {  //ritorna un flow
        return getMoviesUseCase.execute(GetMoviesUC.Params(selection = 0)).cachedIn(viewModelScope)
    }

    fun getSingleMovie(id: Int) {
        viewModelScope.launch {
            _movie.value = getSingleMovieUseCase.execute(GetSingleMovieUC.Params(id))
        }
    }

    suspend fun suspendGetSingleMovie(id: Int):MovieDetailInfo {
        Log.d("XDEBUG", "Colling the service for to get the single movie by id")
       return getSingleMovieUseCase.execute(GetSingleMovieUC.Params(id))
    }

}