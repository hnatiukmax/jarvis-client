package com.bugmakers.jarvistime.data.entity.enums

import com.bugmakers.jarvistime.domain.entity.AuthorizationType.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class AuthorizationType {
    @Json(name = "facebook")
    FACEBOOK,
    @Json(name = "google")
    GOOGLE,
    @Json(name = "email")
    EMAIL
}

internal fun AuthorizationType.toDomainEntity() =
    when (this) {
        AuthorizationType.FACEBOOK -> FACEBOOK
        AuthorizationType.GOOGLE -> GOOGLE
        AuthorizationType.EMAIL -> EMAIL
    }