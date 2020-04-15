package com.bugmakers.jarvistime.presentation.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

internal fun EditText.showKeyboard() {
    isFocusable = true
    isCursorVisible = true
    requestFocus()
    setSelection(text.length)

    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.run {
        showSoftInput(this@showKeyboard, InputMethodManager.SHOW_IMPLICIT)
    }
}