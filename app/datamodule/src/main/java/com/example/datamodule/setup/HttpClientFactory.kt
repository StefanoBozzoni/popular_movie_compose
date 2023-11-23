package com.example.datamodule.setup

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class HttpClientFactory(private val baseDomain: String, private val certificatesPins: List<String>, debug: Boolean) {
    val abstractClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
        if (certificatesPins.size!=0) builder.certificatePinner(createCertificatePinner())
        if (debug)
            builder.addNetworkInterceptor(NetworkModule.createHttpLoggingInterceptor())
        builder.build()
    }

    private fun createCertificatePinner(): CertificatePinner {
        return CertificatePinner.Builder().apply {
            certificatesPins.forEach { this.add(baseDomain, it) }
        }.build()
    }

}