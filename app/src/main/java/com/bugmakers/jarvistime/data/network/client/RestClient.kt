package com.bugmakers.jarvistime.data.network.client

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class RestClient(
    baseUrl: String,
    private val requestAuthenticator: AuthenticationInterceptor,
    private val moshi: Moshi
) {

    private val retrofit = Retrofit.Builder()
        .baseUrl("")
        .client(getClient(requestAuthenticator))
        .addConverterFactory(getDefaultConverterFactory())
        .build()

    private fun getDefaultConverterFactory() = MoshiConverterFactory.create(moshi)
}

