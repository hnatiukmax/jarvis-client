package com.bugmakers.jarvistime.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class TaskResponse(
    @Json(name = "task_id")
    val id : Int,
    @Json(name = "type")
    val type : Int,
    @Json(name = "description")
    val description : String,
    @Json(name = "isCompleted")
    val isCompleted : Boolean
)