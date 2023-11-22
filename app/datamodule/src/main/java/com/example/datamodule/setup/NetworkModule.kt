package com.example.datamodule.setup

import com.example.datamodule.BuildConfig
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

object NetworkModule {

    fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    fun basicHeaderInterceptor() = Interceptor {
        val apiKey = BuildConfig.IMDB_API_KEY
        val request = it.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .build()
        it.proceed(request)
    }

    fun createHeadersAriaInterceptor() = Interceptor {
        val request = it.request().newBuilder()
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .build()
        it.proceed(request)
    }
}