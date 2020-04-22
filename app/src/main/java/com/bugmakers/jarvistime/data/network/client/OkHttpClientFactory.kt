package com.bugmakers.jarvistime.data.network.client

import com.bugmakers.jarvistime.BuildConfig
import com.bugmakers.jarvistime.data.network.authorization.AuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor.Level.*

internal fun getOkHttpClient(
    authenticationInterceptor: AuthorizationInterceptor
): OkHttpClient {

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = getLogLevel()
    }

    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authenticationInterceptor)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()
}

private fun getLogLevel() = if (BuildConfig.DEBUG) BODY else NONE