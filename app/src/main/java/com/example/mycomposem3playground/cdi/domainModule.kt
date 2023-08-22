package com.example.mycomposem3playground.cdi

import com.example.mycomposem3playground.domain.interactors.GetMoviesUC
import com.example.mycomposem3playground.domain.interactors.GetSingleMovieUC
import org.koin.dsl.module

val domainModule = module {
    factory { GetMoviesUC(get()) }
    factory { GetSingleMovieUC(get()) }
}
