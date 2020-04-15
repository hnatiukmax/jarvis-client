package com.bugmakers.jarvistime.domain.entity

import com.bugmakers.jarvistime.data.entity.enums.AuthorizationType.*

enum class AuthorizationType {
    GOOGLE,
    FACEBOOK,
    EMAIL
}

internal fun AuthorizationType.toDataEntity() =
    when (this) {
        AuthorizationType.FACEBOOK -> FACEBOOK
        AuthorizationType.GOOGLE -> GOOGLE
        AuthorizationType.EMAIL -> EMAIL
    }