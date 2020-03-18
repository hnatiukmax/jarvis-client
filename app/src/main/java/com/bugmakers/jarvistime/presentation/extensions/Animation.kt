package com.bugmakers.jarvistime.presentation.extensions

import android.animation.ValueAnimator

internal fun animate(
    range: ClosedRange<Float>,
    duration: Long,
    callback: (ValueAnimator) -> Unit
) = ValueAnimator.ofFloat(range.start, range.endInclusive).apply {
        this.duration = duration
        addUpdateListener(callback)
    }
