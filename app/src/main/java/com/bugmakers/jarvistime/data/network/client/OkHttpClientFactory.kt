package com.bugmakers.jarvistime.data.network.client

import com.bugmakers.jarvistime.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor.Level.*

internal fun getClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level =
        getLogLevel()
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(3, TimeUnit.MINUTES)
        .readTimeout(3, TimeUnit.MINUTES)
        .writeTimeout(3, TimeUnit.MINUTES)
        .build()
}

private fun getLogLevel() = if (BuildConfig.DEBUG) BODY else NONE