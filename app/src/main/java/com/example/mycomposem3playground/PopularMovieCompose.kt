package com.example.mycomposem3playground

import android.app.Application
import com.example.mycomposem3playground.cdi.domainModule
import com.example.mycomposem3playground.cdi.remoteModule
import com.example.mycomposem3playground.cdi.repositoryModule
import com.example.mycomposem3playground.cdi.viewModelModule
import localModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class PopularMovieCompose: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PopularMovieCompose)
            KoinLogger()
            modules(viewModelModule, repositoryModule, localModule, remoteModule, domainModule)
        }
    }
}