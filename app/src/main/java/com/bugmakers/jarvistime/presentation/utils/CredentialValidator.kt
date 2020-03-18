package com.bugmakers.jarvistime.presentation.utils

import androidx.core.text.isDigitsOnly

internal fun String?.ifPasswordCheckValid() = this?.run {
    when {
        isNullOrEmpty() -> false
        isDigitsOnly() -> false
        length < 5 -> false
        else -> true
    }
} ?: false

internal fun String?.ifUsernameCheckValid() = this?.run {
    when {
        isNullOrEmpty() -> false
        indexOf('@') < 0 -> false
        substring(indexOf('@')).isEmpty() -> false
        isDigitsOnly() -> false
        else -> true
    }
} ?: false