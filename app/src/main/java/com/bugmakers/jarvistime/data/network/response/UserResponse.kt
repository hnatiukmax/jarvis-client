package com.bugmakers.jarvistime.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class UserResponse(
    @Json(name = "id")
    val id : Int,
    @Json(name = "username")
    val username : String
)