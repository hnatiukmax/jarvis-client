package com.bugmakers.jarvistime.presentation.extensions

import android.os.Handler

fun Handler.postExecute(time : Long, runnable: () -> Unit) {
    this.postDelayed(runnable, time)
}