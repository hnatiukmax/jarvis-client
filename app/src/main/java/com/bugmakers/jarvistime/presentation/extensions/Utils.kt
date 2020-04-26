package com.bugmakers.jarvistime.presentation.extensions

import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.bugmakers.jarvistime.presentation.entity.enums.AnimationType
import com.bugmakers.jarvistime.presentation.utils.provideAnimResByType

fun Any.log(tag: String = this::class.java.simpleName, message: String) {
    Log.i(tag, message)
}

infix fun <T> Any.tryElse(block: () -> T) =
    try {
        block()
    } catch (ex: Exception) {
        null
    }


internal fun FragmentTransaction.setCustomAnimations(animationType: AnimationType) {
    val (inAnim, outAnim) = provideAnimResByType(animationType)

    this.setCustomAnimations(inAnim, outAnim)
}