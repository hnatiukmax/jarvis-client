package com.bugmakers.jarvistime.presentation.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.color(@ColorRes colorRes : Int) =
    ContextCompat.getColor(this, colorRes)

fun Context.string(@StringRes stringRes : Int) =
    getString(stringRes)

fun Context.string(@StringRes stringRes : Int, vararg params: Any) =
    getString(stringRes, *params)

fun Context.integer(@IntegerRes integerRes : Int) =
    resources.getInteger(integerRes)

fun Context.dimen(@DimenRes dimenRes : Int) =
    resources.getDimension(dimenRes)