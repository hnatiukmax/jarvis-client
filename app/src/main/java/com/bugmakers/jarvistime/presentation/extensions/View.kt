package com.bugmakers.jarvistime.presentation.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.graphics.drawable.DrawableCompat

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

internal fun View.visible() {
    this.visibility = View.VISIBLE
}

internal fun View.gone() {
    this.visibility = View.GONE
}

internal fun View.invisible() {
    this.visibility = View.INVISIBLE
}

internal fun View.color(@ColorRes colorResId: Int) = context.color(colorResId)

internal fun View.string(@StringRes stringResId: Int) = context.string(stringResId)

internal fun View.dimen(@IntegerRes integerResId: Int) = context.integer(integerResId)

internal fun View.setBackgroundTint(colorResId: Int) {
    val unwrappedBottomDrawable = background
    val wrappedBottomDrawable = DrawableCompat.wrap(unwrappedBottomDrawable)

    wrappedBottomDrawable.setTint(colorResId)
}



