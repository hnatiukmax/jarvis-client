package com.bugmakers.jarvistime.presentation.bindingadapters

import android.animation.ArgbEvaluator
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.presentation.extensions.animateByUpdateListener
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

        animateByUpdateListener(0.0f..1.0f, 300) {
            val color = argbEvaluator.evaluate(it, color(startColor), color(endColor)) as Int

            setBackgroundTint(color)
        }.start()
    }
}