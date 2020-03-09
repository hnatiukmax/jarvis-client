package com.bugmakers.jarvistime.presentation.extensions

import android.util.Log

fun Any.log(
    tag : String = this::class.java.name,
    message : String = this::class.java.simpleName + " logMessage"
) {
    Log.i(tag, message)
}