package com.bugmakers.jarvistime.presentation.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.bugmakers.jarvistime.presentation.view.ProgressView

@BindingAdapter("isShowProgress")
internal fun isShowProgress(view: ProgressView, isVisible: Boolean) {
    if (view.visibility != View.VISIBLE && isVisible) {
        view.showProgress()
    } else {
        view.hideProgress()
    }
}