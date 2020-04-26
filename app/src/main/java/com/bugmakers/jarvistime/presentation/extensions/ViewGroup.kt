package com.bugmakers.jarvistime.presentation.extensions

import android.view.View
import android.view.ViewGroup

internal inline fun <reified T : View> ViewGroup.childAt(index: Int) =
    getChildAt(index) as? T