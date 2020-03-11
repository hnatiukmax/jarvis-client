package com.bugmakers.jarvistime.data.network.mapper

import com.squareup.moshi.Moshi
import java.lang.IllegalArgumentException

internal class MoshiSerializer(private val moshi: Moshi) : Serializer {
    override fun <T> toJson(type: Class<T>, value: T?) =
        moshi.adapter(type).toJson(value) ?: throw IllegalArgumentException()
}