package com.example.mycomposem3playground.cdi

import com.example.domainmodule.interactors.GetMoviesUC
import com.example.domainmodule.interactors.GetSingleMovieUC
import com.example.domainmodule.interactors.UpdateFavorites
import org.koin.dsl.module

val domainModule = module {
    factory { GetMoviesUC(get()) }
    factory { GetSingleMovieUC(get()) }
    factory { UpdateFavorites(get()) }
}
