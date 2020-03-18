package com.bugmakers.jarvistime.presentation.extensions

import android.util.Log

fun log(
    tag : String = "yourLogTag",
    message : String = "yourLogMessage"
) {
    Log.i(tag, message)
}