package com.bugmakers.jarvistime.data.network.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class TaskRequestBody(
    @Json(name = "type")
    val type : Int,
    @Json(name = "description")
    val description : String,
    @Json(name = "isCompleted")
    val isCompleted : Boolean
)