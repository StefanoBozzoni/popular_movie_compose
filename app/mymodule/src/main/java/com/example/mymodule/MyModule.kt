package com.example.mymodule

import android.content.Context
import android.content.Intent

object MyModule {

    lateinit var appContext: Context

    fun startActivity() {
        val i = Intent(appContext, MainActivity::class.java)
        appContext.startActivity(i)
    }

}