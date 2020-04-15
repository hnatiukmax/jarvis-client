package com.bugmakers.jarvistime.presentation.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.bugmakers.jarvistime.presentation.extensions.gone
import com.bugmakers.jarvistime.presentation.extensions.visible
import com.bugmakers.jarvistime.presentation.view.ProgressView
import me.zhanghai.android.materialprogressbar.MaterialProgressBar

@BindingAdapter("showProgress")
internal fun showProgress(view: ProgressView, isVisible: Boolean) {
    if (view.visibility != View.VISIBLE && isVisible) {
        view.showProgress()
    } else {
        view.hideProgress()
    }
}

@BindingAdapter("showProgress")
internal fun showProgress(view: MaterialProgressBar, isVisible: Boolean) {
    if (view.visibility != View.VISIBLE && isVisible) {
        view.visible()
    } else {
        view.gone()
    }
}

