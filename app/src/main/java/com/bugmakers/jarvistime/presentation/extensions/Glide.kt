package com.bugmakers.jarvistime.presentation.extensions

import android.graphics.drawable.Drawable
import com.bugmakers.jarvistime.presentation.utils.listeners.SimpleRequestListener
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.target.Target

typealias OnResourceReady<T> = (
    resource: T?,
    model: Any?,
    target: Target<T>?,
    dataSource: DataSource?,
    isFirstResource: Boolean
) -> Boolean

typealias OnLoadFailedReady<T> = (
    e: GlideException?,
    model: Any?,
    target: Target<T>?,
    isFirstResource: Boolean
) -> Boolean

internal inline fun <T> RequestBuilder<T>.doOnResourceReady(
    crossinline doOnResourceReady: OnResourceReady<T>
): RequestBuilder<T> where T : Drawable {
    val listener = object : SimpleRequestListener<T>() {
        override fun onResourceReady(
            resource: T?,
            model: Any?,
            target: Target<T>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            return doOnResourceReady(resource, model, target, dataSource, isFirstResource)
        }
    }
    return addListener(listener)
}

internal inline fun <T> RequestBuilder<T>.doOnLoadFailed(
    crossinline doOnLoadFailed: OnLoadFailedReady<T>
): RequestBuilder<T> where T : Drawable {
    val listener = object : SimpleRequestListener<T>() {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<T>?,
            isFirstResource: Boolean
        ): Boolean {
            return doOnLoadFailed(e, model, target, isFirstResource)
        }
    }
    return addListener(listener)
}