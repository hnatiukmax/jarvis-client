package com.bugmakers.jarvistime.domain.entity

internal data class UserInfo(
    val id: Int,
    val username: String,
    val photo: String,
    val authType: AuthorizationType
)