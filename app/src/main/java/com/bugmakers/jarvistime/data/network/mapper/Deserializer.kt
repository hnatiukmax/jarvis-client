package com.bugmakers.jarvistime.data.network.mapper

internal interface Deserializer {

    fun <T>fromJson(type: Class<T>, json: String): T
}