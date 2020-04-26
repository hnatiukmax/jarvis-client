package com.bugmakers.jarvistime.presentation.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
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

internal inline fun <reified T : View> View.postDelayed(
    delayInMillis: Long,
    crossinline action: (T) -> Unit
): Runnable {
    val runnable = Runnable { action(this as T) }
    postDelayed(runnable, delayInMillis)
    return runnable
}

/*
 * Resources
 **/
internal fun View.color(@ColorRes colorResId: Int) = context.color(colorResId)

internal fun View.string(@StringRes stringResId: Int) = context.string(stringResId)

internal fun View.string(@StringRes stringResId: Int, vararg params: Any) =
    context.string(stringResId, *params)

internal fun View.integer(@IntegerRes integerRes: Int) = context.integer(integerRes)

internal fun View.dimen(@DimenRes dimenResId: Int) = context.dimen(dimenResId)


/*
 * Size values
 **/
internal fun View.px2Dp(value: Int) = context.px2Dp(value)

internal fun View.dp2Px(value: Int) = context.dp2Px(value)

internal fun View.dp2Px(value: Float) = context.dp2Px(value)

/*
 * View properties
 **/
internal fun View.visible() {
    this.visibility = View.VISIBLE
}

internal fun View.gone() {
    this.visibility = View.GONE
}

internal fun View.invisible() {
    this.visibility = View.INVISIBLE
}

internal fun View.setBackgroundTint(colorResId: Int): Boolean {
    return background?.let {
        val wrappedBottomDrawable = DrawableCompat.wrap(it)

        wrappedBottomDrawable.setTint(colorResId)

        true
    } ?: false
}

internal fun View.setRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
    setBackgroundResource(resourceId)
}

internal fun View.setCircleRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, this, true)
    setBackgroundResource(resourceId)
}


