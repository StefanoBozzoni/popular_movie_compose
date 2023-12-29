package com.example.mycomposem3playground

import android.app.Application
import com.example.bridge_di_module.domainModule
import com.example.bridge_di_module.remoteModule
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
            modules(viewModelModule, localModule, remoteModule, domainModule)
        }
    }
}