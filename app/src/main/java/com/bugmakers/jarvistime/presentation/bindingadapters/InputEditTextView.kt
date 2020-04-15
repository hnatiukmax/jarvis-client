package com.bugmakers.jarvistime.presentation.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bugmakers.jarvistime.presentation.view.InputEditTextView

@BindingAdapter("hasError")
internal fun hasError(view: InputEditTextView, isError: Boolean?) {
    if (isError != null) {
        view.hasError(isError)
    }
}

@BindingAdapter("text")
internal fun setInputViewText(view: InputEditTextView, text: String?) {
    if (text != null && view.text != text) {
        view.text = text
    }
}

@InverseBindingAdapter(attribute = "text")
internal fun getInputViewText(view: InputEditTextView) : String {
    return view.text
}

@BindingAdapter("textAttrChanged")
internal fun setInputViewListener(view: InputEditTextView, listener: InverseBindingListener?) {
    view.onTextChangedListener = {
        listener?.onChange()
    }
}