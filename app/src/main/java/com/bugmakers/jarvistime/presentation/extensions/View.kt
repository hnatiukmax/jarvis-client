package com.bugmakers.jarvistime.presentation.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View

val View.activity: Activity?
    get() = run {
        var context: Context = context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }