package com.bugmakers.jarvistime.presentation.extensions

import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.bugmakers.jarvistime.presentation.entity.enums.AnimationType
import com.bugmakers.jarvistime.presentation.utils.getAnimResByType

fun Any.log(tag: String = this::class.java.simpleName, message: String) {
    Log.i(tag, message)
}

internal fun FragmentTransaction.setCustomAnimations(animationType: AnimationType) {
    val (inAnim, outAnim) = getAnimResByType(animationType)

    this.setCustomAnimations(inAnim, outAnim)
}