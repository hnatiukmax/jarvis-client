package com.bugmakers.jarvistime.data.network.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SocialAuthRequestBody(
    @Json(name = "provider")
    val provider : String,
    @Json(name = "token")
    val token : String
)