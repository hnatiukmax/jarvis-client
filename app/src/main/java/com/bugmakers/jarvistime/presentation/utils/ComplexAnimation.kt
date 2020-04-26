package com.bugmakers.jarvistime.presentation.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.bugmakers.jarvistime.presentation.entity.enums.Margin
import com.bugmakers.jarvistime.presentation.entity.enums.SizeType
import com.bugmakers.jarvistime.presentation.extensions.*
import com.bugmakers.jarvistime.presentation.extensions.animateByUpdateListener
import com.bugmakers.jarvistime.presentation.extensions.animateSize
import net.cachapa.expandablelayout.util.FastOutSlowInInterpolator

internal fun animate(view: View) = ComplexAnimation(view)

internal class ComplexAnimation<T>(
    private val view: T
) where T : View {

    private var complexDuration = 0L
    private var thenSelected = false
    private var animationSetList = mutableListOf<MutableSet<Pair<Animator, Long>>>()

    private var onEndCallback: (() -> Unit)? = null

    companion object {
        private const val NO_DURATION = -1L
    }

    private fun addAnimation(item: Pair<Animator, Long>) {
        if (animationSetList.isEmpty()) {
            animationSetList.add(mutableSetOf(item))
            return
        }

        if (thenSelected) {
            animationSetList.add(mutableSetOf(item))
            thenSelected = false
        } else {
            animationSetList.last().add(item)
        }
    }

    fun duration(duration: Long) = apply {
        complexDuration = duration
    }

    fun width(to: Int, duration: Long = NO_DURATION) = apply {
        return view.run {
            val widthAnimation = animateSize(SizeType.WIDTH, measuredWidth..to)

            addAnimation(widthAnimation to duration)

            this@ComplexAnimation
        }
    }

    fun height(to: Int, duration: Long = NO_DURATION) = apply {
        view.apply {
            val heightAnimation = animateSize(SizeType.HEIGHT, measuredWidth..to)

            addAnimation(heightAnimation to duration)
        }
    }

    fun alpha(to: Float, duration: Long = NO_DURATION) = apply {
        view.run {
            val animation = alphaWithAnimation(alpha..to, 0)

            addAnimation(animation to duration)
        }
    }

    fun visibility(isVisible: Boolean, duration: Long = NO_DURATION) = apply {
        view.run {
            val animation = visibleWithAnimation(isVisible, 0)

            addAnimation(animation to duration)
        }
    }

    fun horizontalBias(to: Float, duration: Long = NO_DURATION) = apply {
        view.run {
            if (layoutParams !is ConstraintLayout.LayoutParams) {
                return@run
            }

            val layoutParams = layoutParams as ConstraintLayout.LayoutParams

            val anim = animateByUpdateListener(layoutParams.horizontalBias..to, 0) {
                layoutParams.horizontalBias = it
                this.layoutParams = layoutParams
            }

            addAnimation(anim to duration)
        }
    }

    fun verticalBias(to: Float, duration: Long = NO_DURATION) = apply {
        view.run {
            if (layoutParams !is ConstraintLayout.LayoutParams) {
                return@run
            }

            val layoutParams = layoutParams as ConstraintLayout.LayoutParams

            val anim = animateByUpdateListener(layoutParams.verticalBias..to, 0) {
                layoutParams.verticalBias = it
                this.layoutParams = layoutParams
            }

            addAnimation(anim to duration)
        }
    }

    fun drawableTint(
        @ColorRes startColor: Int,
        @ColorRes endColor: Int,
        duration: Long = NO_DURATION
    ) = apply {
        view.run {
            if (this !is ImageView) {
                throw TypeCastException("view is not ImageView")
            }

            val animation = setDrawableTintWithAnimation(startColor, endColor, 0)

            addAnimation(animation to duration)
        }
    }

    fun leftMargin(to: Int, duration: Long = NO_DURATION) = apply {
        view.run {
            val animation = animateMargin(Margin.LEFT, marginLeft..to, 0)

            addAnimation(animation to duration)
        }
    }

    fun rightMargin(to: Int, duration: Long = NO_DURATION) = apply {
        view.run {
            val animation = animateMargin(Margin.RIGHT, marginRight..to, 0)

            addAnimation(animation to duration)
        }
    }

    fun topMargin(to: Int, duration: Long = NO_DURATION) = apply {
        view.run {
            val animation = animateMargin(Margin.TOP, marginTop..to, 0)

            addAnimation(animation to duration)
        }
    }

    fun bottomMargin(to: Int, duration: Long = NO_DURATION) = apply {
        view.run {
            val animation = animateMargin(Margin.BOTTOM, marginBottom..to, 0)

            addAnimation(animation to duration)
        }
    }

    fun horizontalMargin(to: Int, duration: Long = NO_DURATION) = apply {
        view.run {
            val leftAnimation = animateMargin(Margin.LEFT, marginLeft..to, 0)
            val rightAnimation = animateMargin(Margin.RIGHT, marginRight..to, 0)

            addAnimation(leftAnimation to duration)
            addAnimation(rightAnimation to duration)
        }
    }

    fun rotate(degree: Float, forClock: Boolean, duration: Long = NO_DURATION) = apply {
        view.run {
            val mDegree = if (forClock) degree else 0f

            val animation = ObjectAnimator.ofFloat(this, View.ROTATION, mDegree).apply {
                interpolator = FastOutSlowInInterpolator()
            }

            addAnimation(animation to duration)
        }
    }

    fun then() = apply {
        thenSelected = true
    }

    fun doOnEnd(onEnd: () -> Unit) = apply {
        onEndCallback = onEnd
    }

    fun start() {
        startSet(0)
    }

    private fun startSet(listPosition: Int) {
        val set = tryElse {
            animationSetList[listPosition]
        } ?: return

        if (set == animationSetList.last()) {
            set.last().first.doOnEnd {
                onEndCallback?.invoke()
            }
        }

        set.forEach {
            it.first.also { animator ->
                animator.duration = if (it.second == NO_DURATION) complexDuration else it.second

                if (animator == set.last().first) {
                    animator.doOnEnd {
                        startSet(listPosition + 1)
                    }
                }
            }.start()
        }
    }
}