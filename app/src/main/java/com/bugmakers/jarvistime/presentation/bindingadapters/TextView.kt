package com.bugmakers.jarvistime.presentation.bindingadapters

import android.animation.ArgbEvaluator
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.presentation.extensions.animateByUpdateListener
import com.bugmakers.jarvistime.presentation.extensions.color
import com.bugmakers.jarvistime.presentation.extensions.integer
import com.bugmakers.jarvistime.presentation.extensions.setBackgroundTint

@BindingAdapter("isEnabled")
internal fun isEnabled(view: TextView, isEnabled: Boolean?) {
    view.apply {
        val (startColor, endColor) = when (isEnabled) {
            true -> R.color.mainButtonDisabled to R.color.main_button_new
            false-> R.color.main_button_new to R.color.mainButtonDisabled
            else -> {
                return@apply
            }
        }

        val argbEvaluator = ArgbEvaluator()
        val animationDuration = integer(android.R.integer.config_mediumAnimTime).toLong()
        val onAnimationEnd = {
            this.isClickable = isEnabled
        }

        animateByUpdateListener(0.0f..1.0f, animationDuration, onAnimationEnd) {
            val color = argbEvaluator.evaluate(it, color(startColor), color(endColor)) as Int

            setBackgroundTint(color)
        }.start()
    }
}