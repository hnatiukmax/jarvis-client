package com.bugmakers.jarvistime.presentation.extensions

import android.util.Log

fun Any.log(tag: String = this::class.java.simpleName, message: String) {
    Log.i(tag, message)
}