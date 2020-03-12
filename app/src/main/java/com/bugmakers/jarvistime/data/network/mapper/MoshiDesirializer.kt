package com.bugmakers.jarvistime.data.network.mapper

import com.squareup.moshi.Moshi

internal class MoshiDesirializer(private val moshi: Moshi) : Deserializer {
    override fun <T> fromJson(type: Class<T>, json: String): T {
        return moshi.adapter(type).fromJson(json) ?: throw IllegalArgumentException()
    }
}