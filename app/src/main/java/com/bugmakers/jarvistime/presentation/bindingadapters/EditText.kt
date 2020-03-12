package com.bugmakers.jarvistime.presentation.bindingadapters

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.bugmakers.jarvistime.R


@BindingAdapter("hasError")
fun hasError(view: EditText, isError: Boolean?) {
    if (isError == true) {
        view.setBackgroundResource(R.drawable.transparent_background_with_underline_error)
    } else {
        view.setBackgroundResource(R.drawable.transparent_background_with_underline_normal)
    }
}