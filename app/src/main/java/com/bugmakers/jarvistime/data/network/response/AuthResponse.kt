package com.bugmakers.jarvistime.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class AuthResponse(
    @Json(name = "username")
    val username: String,
    @Json(name = "token")
    val token: String
)