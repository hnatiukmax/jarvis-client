package com.bugmakers.jarvistime.domain.entity

enum class AuthenticationType(val provider : String) {
    GOOGLE("google"),
    FACEBOOK("facebook"),
    EMAIL("email")
}