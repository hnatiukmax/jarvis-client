package com.bugmakers.jarvistime.data.network.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.bugmakers.jarvistime.data.entity.enums.AuthorizationType

@JsonClass(generateAdapter = true)
data class SocialAuthRequestBody(
    @Json(name = "provider")
    val provider : AuthorizationType,
    @Json(name = "token")
    val token : String
)