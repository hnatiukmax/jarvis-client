package com.bugmakers.jarvistime.data.network.client

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

internal class RestClient(
    private val baseUrl: String,
    private val okHttpClient: OkHttpClient,
    private val moshi: Moshi
) {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(getDefaultConverterFactory())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private fun getDefaultConverterFactory() = MoshiConverterFactory.create(moshi)
}

