package com.bugmakers.jarvistime.presentation.bindingadapters

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.presentation.extensions.*
import com.bugmakers.jarvistime.presentation.utils.animate
import com.bumptech.glide.Glide

@BindingAdapter("imageSource")
internal fun loadImageSource(view: ImageView, imageSource: String?) {
    if (imageSource.isNullOrEmpty()) {
        view.setImageResource(R.drawable.ic_avatar_placeholder)
    }

    Glide.with(view.context)
        .load(imageSource)
        .dontAnimate()
        .doOnResourceReady(getOnResourceReady(view))
        .into(view)
}

private fun getOnResourceReady(view: View): OnResourceReady<Drawable> = { resource, _, _, _, _ ->
    view.postDelayed<ImageView>(0L) {
        val duration = it.integer(android.R.integer.config_shortAnimTime).toLong()

        if (it.drawable == resource) {
            return@postDelayed
        }

        animate(it)
            .alpha(0f, duration)
            .doOnEnd {
                it.setImageDrawable(resource)
                it.alphaWithAnimation(0f..1f, duration).start()
            }
            .start()
    }
    true
}