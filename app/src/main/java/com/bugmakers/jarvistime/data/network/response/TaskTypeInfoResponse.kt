package com.bugmakers.jarvistime.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class TaskTypeInfoResponse(
    @Json(name = "type")
    val type : Int,
    @Json(name = "all_count")
    val allCount : Int,
    @Json(name = "completed_count")
    val completedCount : Int
)