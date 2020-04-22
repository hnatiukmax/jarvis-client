package com.bugmakers.jarvistime.data.local.kotpref

import com.chibatching.kotpref.KotprefModel

internal class UserInfoPref : KotprefModel() {
    var username by stringPref()
    var token by stringPref()

    fun clean() {
        username = ""
        token = ""
    }
}