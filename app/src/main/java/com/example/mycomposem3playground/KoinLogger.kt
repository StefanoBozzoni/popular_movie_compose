package com.example.mycomposem3playground

import android.util.Log
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE

class KoinLogger : Logger() {
    override fun display(level: Level, msg: MESSAGE) {
        Log.i("KOIN",msg)
    }
}