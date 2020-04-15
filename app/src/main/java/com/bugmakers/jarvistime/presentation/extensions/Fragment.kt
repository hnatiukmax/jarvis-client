package com.bugmakers.jarvistime.presentation.extensions

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bugmakers.jarvistime.presentation.entity.enums.AnimationType

internal fun Fragment.goTo(
    to: Class<out Activity>,
    animationType: AnimationType? = null,
    bundle: Bundle? = null,
    close: Boolean = true
) {
    activity?.goTo(to, animationType, bundle, close)
}

internal fun Fragment.closeWithTransition(animationType: AnimationType) {
    activity?.closeWithTransition(animationType)
}