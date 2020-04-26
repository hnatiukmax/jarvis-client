package com.bugmakers.jarvistime.presentation.extensions

import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat

internal fun TextView.setCompoundDrawableTint(colorRes: Int) {
    compoundDrawables.filterNotNull().forEach {
        val wrappedBottomDrawable = DrawableCompat.wrap(it)

        wrappedBottomDrawable.setTint(colorRes)
    }
}

internal val TextView.textWidth: Float
    get() {
        return paint.measureText(text.toString())
    }