@file:Suppress("UNCHECKED_CAST")

package com.bugmakers.jarvistime.presentation.extensions

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import com.bugmakers.jarvistime.presentation.entity.enums.Margin
import com.bugmakers.jarvistime.presentation.entity.enums.SizeType
import com.bugmakers.jarvistime.presentation.view.BottomNavigationMenu
import net.cachapa.expandablelayout.util.FastOutSlowInInterpolator

/**
 * TODO
 *
 * @param sizeType
 * @param range
 * @param duration
 * @param onAnimationEnd
 * @return
 */
internal fun View.animateSize(
    sizeType: SizeType,
    range: IntRange,
    duration: Long = 0,
    onAnimationEnd: (() -> Unit)? = null
): ValueAnimator {

    val layoutParams = layoutParams

    val updateListener = when (sizeType) {
        SizeType.HEIGHT -> { it: Int ->
            layoutParams.height = it

            this@animateSize.layoutParams = layoutParams
        }

        SizeType.WIDTH -> { it: Int ->
            layoutParams.width = it

            this@animateSize.layoutParams = layoutParams
        }
    }

    return animateByUpdateListener(range, duration, onAnimationEnd, updateListener)
}

/**
 * TODO
 *
 * @param margins
 * @param duration
 * @param onAnimationEnd
 */
internal fun View.animateMargins(
    vararg margins: Pair<Margin, IntRange>,
    duration: Long,
    onAnimationEnd: (() -> Unit)? = null
) = Array<Animator>(margins.size) {
    animateMargin(margins[it].first, margins[it].second, duration, onAnimationEnd)
}

/**
 * TODO
 *
 * @param margin
 * @param range
 * @param duration
 * @param onAnimationEnd
 */
internal fun View.animateMargin(
    margin: Margin,
    range: IntRange,
    duration: Long,
    onAnimationEnd: (() -> Unit)? = null
) = animateMargin(margin, range.first, range.last, duration, onAnimationEnd)

/**
 * TODO
 *
 * @param margin
 * @param from
 * @param to
 * @param duration
 * @param onAnimationEnd
 * @return
 */
internal fun View.animateMargin(
    margin: Margin,
    from: Int = (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin,
    to: Int,
    duration: Long,
    onAnimationEnd: (() -> Unit)? = null
): ValueAnimator {

    val layoutParams = layoutParams as ViewGroup.MarginLayoutParams

    val updateListener = when (margin) {
        Margin.BOTTOM -> { it: Int ->
            layoutParams.bottomMargin = it
            this.layoutParams = layoutParams
        }
        Margin.TOP -> { it: Int ->
            layoutParams.topMargin = it
            this.layoutParams = layoutParams
        }
        Margin.RIGHT -> { it: Int ->
            layoutParams.rightMargin = it
            this.layoutParams = layoutParams
        }
        Margin.LEFT -> { it: Int ->
            layoutParams.leftMargin = it
            this.layoutParams = layoutParams
        }
    }

    return animateByUpdateListener(from..to, duration, onAnimationEnd, updateListener)
}

/**
 * TODO
 *
 * @param T
 * @param range
 * @param duration
 * @param onAnimationEnd
 * @param updateListener
 * @return
 */
internal fun <T : Comparable<T>> animateByUpdateListener(
    range: ClosedRange<T>,
    duration: Long,
    onAnimationEnd: (() -> Unit)? = null,
    updateListener: (T) -> Unit
): ValueAnimator {
    val valueAnimator = when (range.start) {
        is Int -> ValueAnimator.ofInt(range.start as Int, range.endInclusive as Int)
        is Float -> ValueAnimator.ofFloat(range.start as Float, range.endInclusive as Float)
        else -> throw IllegalArgumentException("ClosedRange is not range of Int or Float")
    }

    return valueAnimator.apply {
        this.duration = duration
        this.interpolator = FastOutSlowInInterpolator()

        addUpdateListener {
            updateListener.invoke(
                it.animatedValue as? T
                    ?: throw IllegalArgumentException("ClosedRange is not range of Int or Float")
            )
        }

        doOnEnd { onAnimationEnd?.invoke() }
    }
}

/**
 * TODO
 *
 * @param visible
 * @param duration
 * @param onAnimationEnd
 */
internal fun View.visibleWithAnimation(
    visible: Boolean,
    duration: Long,
    onAnimationEnd: (() -> Unit)? = null
) : Animator {
    val alphaRange = if (visible) 0f..1f else 1f..0f

    if (visible) {
        alpha = 0f
        visibility = View.VISIBLE
    } else {
        alpha = 1f
        visibility = View.GONE
    }

    return alphaWithAnimation(alphaRange, duration) {
        if (!visible) {
            visibility = View.GONE
        }
        onAnimationEnd?.invoke()
    }
}

/**
 * TODO
 *
 * @param range
 * @param duration
 * @param onAnimationEnd
 */
internal fun View.alphaWithAnimation(
    range: ClosedRange<Float>,
    duration: Long,
    onAnimationEnd: (() -> Unit)? = null
) = ObjectAnimator.ofFloat(this, "alpha", range.start, range.endInclusive).apply {
    this.duration = duration
    doOnEnd { onAnimationEnd?.invoke() }
}

fun ImageView.setDrawableTintWithAnimation(
    startColor: Int,
    endColor: Int,
    duration: Long,
    onAnimationEnd: (() -> Unit)? = null
): Animator {
    val argbEvaluator = ArgbEvaluator()

    return animateByUpdateListener(0.0f..1.0f, duration, onAnimationEnd) {
        val color = argbEvaluator.evaluate(it, color(startColor), color(endColor)) as Int

        setBackgroundTint(color)
    }
}

fun TextView.setDrawableTintWithAnimation(
    startColor: Int,
    endColor: Int,
    duration: Long,
    onAnimationEnd: (() -> Unit)? = null
): Animator {
    val argbEvaluator = ArgbEvaluator()

    return animateByUpdateListener(0.0f..1.0f, duration, onAnimationEnd) {
        val color = argbEvaluator.evaluate(it, color(startColor), color(endColor)) as Int

        setCompoundDrawableTint(color)
    }
}