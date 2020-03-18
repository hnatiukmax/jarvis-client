package com.bugmakers.jarvistime.presentation.bindingadapters

import android.animation.ArgbEvaluator
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.presentation.extensions.animate
import com.bugmakers.jarvistime.presentation.extensions.color
import com.bugmakers.jarvistime.presentation.extensions.setBackgroundTint

@BindingAdapter("hasError")
fun hasError(view: EditText, isError: Boolean?) {
    view.apply {
        val (startColor, endColor) = when (isError) {
            isSaveFromParentEnabled -> return
            true -> R.color.editTextUnderlineNormal to R.color.editTextErrorUnderlineColor
            false -> R.color.editTextErrorUnderlineColor to R.color.editTextUnderlineNormal
            null -> {
                setBackgroundTint(color(R.color.editTextUnderlineNormal))
                isSaveFromParentEnabled = false
                return
            }
        }

        isSaveFromParentEnabled = isError

        val argbEvaluator = ArgbEvaluator()

        animate(0.0f..1.0f, 300) {
            val position = it.animatedValue as Float
            val color = argbEvaluator.evaluate(
                position,
                color(startColor),
                color(endColor)
            ) as Int

            setBackgroundTint(color)
        }.start()
    }
}