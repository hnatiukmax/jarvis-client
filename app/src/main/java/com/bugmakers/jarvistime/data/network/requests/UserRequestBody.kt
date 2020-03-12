package com.bugmakers.jarvistime.data.network.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class UserRequestBody(
    @Json(name = "username")
    val username : String,
    @Json(name = "password")
    val password : String
)