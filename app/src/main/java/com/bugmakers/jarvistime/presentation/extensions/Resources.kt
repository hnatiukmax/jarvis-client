package com.bugmakers.jarvistime.presentation.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.color(@ColorRes colorRes : Int) =
    ContextCompat.getColor(this, colorRes)

fun Context.string(@StringRes stringRes : Int) =
    getString(stringRes)

fun Context.integer(@IntegerRes integerRes : Int) =
    resources.getInteger(integerRes)