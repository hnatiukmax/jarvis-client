package com.bugmakers.jarvistime.presentation.utils.listeners

import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

internal open class SimpleRequestListener<T> : RequestListener<T> {

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<T>?,
        isFirstResource: Boolean
    ): Boolean {
        return true
    }

    override fun onResourceReady(
        resource: T?,
        model: Any?,
        target: Target<T>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        return true
    }
}