package com.bugmakers.jarvistime.data.network.client

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

internal class RestClient(
    baseUrl: String,
    private val requestAuthenticator: AuthenticationInterceptor,
    private val moshi: Moshi
) {

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(getClient())
        .addConverterFactory(getDefaultConverterFactory())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private fun getDefaultConverterFactory() = MoshiConverterFactory.create(moshi)
}

