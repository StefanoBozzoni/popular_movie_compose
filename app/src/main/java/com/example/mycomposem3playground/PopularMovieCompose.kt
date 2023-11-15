package com.example.mycomposem3playground

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PopularMovieCompose: Application() {
    override fun onCreate() {
        super.onCreate()
        /*
        startKoin {
            androidContext(this@PopularMovieCompose)
            KoinLogger()
            modules(viewModelModule, repositoryModule, localModule, remoteModule, domainModule)
        }

         */
    }
}