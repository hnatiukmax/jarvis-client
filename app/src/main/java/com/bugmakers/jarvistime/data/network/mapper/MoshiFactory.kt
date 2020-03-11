package com.bugmakers.jarvistime.data.network.mapper


import com.squareup.moshi.Moshi

internal class MoshiFactory {

    fun newInstance() = Moshi.Builder()
        .build()
}