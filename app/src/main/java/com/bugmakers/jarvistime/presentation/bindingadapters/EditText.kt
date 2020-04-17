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
            true -> R.color.edit_text_underline_normal to R.color.edit_text_error_underline_color
            false -> R.color.edit_text_error_underline_color to R.color.edit_text_underline_normal
            null -> {
                setBackgroundTint(color(R.color.edit_text_underline_normal))
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