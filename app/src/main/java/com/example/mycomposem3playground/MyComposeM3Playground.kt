package com.example.mycomposem3playground

import android.app.Application
import com.example.mycomposem3playground.cdi.domainModule
import com.example.mycomposem3playground.cdi.remoteModule
import com.example.mycomposem3playground.cdi.repositoryModule
import com.example.mycomposem3playground.cdi.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyComposeM3Playground: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyComposeM3Playground)
            KoinLogger()
            modules(viewModelModule, repositoryModule, remoteModule, domainModule)
        }
    }
}