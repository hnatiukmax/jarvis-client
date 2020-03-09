package com.bugmakers.jarvistime.presentation.extensions

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast

// !!! Use toasty library (toast with steroids)
fun Context.showToast(
    message : String = this::class.java.simpleName + " toastMessage",
    toastLength : Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, message, toastLength).show()
}

fun Context.getMetricsRatio(): Double {
    val windowsManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val displayMetrics = DisplayMetrics()

    with(windowsManager.defaultDisplay) {
        getMetrics(displayMetrics)
    }

    return (displayMetrics.heightPixels.toDouble() / displayMetrics.widthPixels)
}

fun Context.px2Dp(size: Int): Int = (size / Resources.getSystem().displayMetrics.density).toInt()

fun Context.dp2Px(size: Int): Int = (size * Resources.getSystem().displayMetrics.density).toInt()

fun Context.dp2Px(size: Float): Float = size * Resources.getSystem().displayMetrics.density