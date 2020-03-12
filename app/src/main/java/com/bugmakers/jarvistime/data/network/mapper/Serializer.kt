package com.bugmakers.jarvistime.data.network.mapper

internal interface Serializer {

    fun <T>toJson(type : Class<T>, value : T?) : String
}