package com.bugmakers.jarvistime.presentation.bindingadapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bugmakers.jarvistime.presentation.extensions.*
import com.bumptech.glide.Glide


@SuppressLint("CheckResult")
@BindingAdapter("imageSource")
internal fun loadImageSource(view: ImageView, imageSource: String?) {
    if (imageSource == null) {
        return
    }

    Glide.with(view)
        .load(imageSource)
        .centerCrop()
        .dontAnimate()
        .doOnResourceReady(getOnResourceReady(view))
        .submit()
}

private fun getOnResourceReady(view: View): OnResourceReady<Drawable> = { resource, _, _, _, _ ->
    view.postDelayed(0L) { it ->
        val duration = it.integer(android.R.integer.config_shortAnimTime).toLong()

        it.alphaWithAnimation(1f..0f, duration) {
            (it as? ImageView)?.setImageDrawable(resource)
            it.alphaWithAnimation(0f..1f, duration).start()
        }.start()
    }
    true
}