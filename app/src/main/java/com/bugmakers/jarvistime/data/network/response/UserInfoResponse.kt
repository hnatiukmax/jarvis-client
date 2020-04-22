package com.bugmakers.jarvistime.data.network.response

import com.bugmakers.jarvistime.data.entity.enums.AuthorizationType
import com.bugmakers.jarvistime.data.entity.enums.toDomainEntity
import com.bugmakers.jarvistime.domain.entity.UserInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class UserInfoResponse(
    @Json(name = "user_id")
    val id: Int,
    @Json(name = "login")
    val username: String,
    @Json(name = "photo")
    val photo: String,
    @Json(name = "auth_type")
    val authType: AuthorizationType
)

internal fun UserInfoResponse.toDomainEntity() = UserInfo(
    id = id,
    username = username,
    photo = photo,
    authType = authType.toDomainEntity()
)