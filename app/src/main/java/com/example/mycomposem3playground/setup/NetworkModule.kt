package com.example.mycomposem3playground.setup

import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

object NetworkModule {

    fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    fun basicHeaderInterceptor() = Interceptor {
        val request = it.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0MzVkMGVmNWJlYzFhN2M2YjFlNWVhYzQ3OThkM2Y5NiIsInN1YiI6IjY0YTZkNTAwY2FlNjMyMWIyODM3NDY5OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.iEBLd4dXlCN8VKL0OObtgT4gCGRAegBq9HkUOBaWGaQ")
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