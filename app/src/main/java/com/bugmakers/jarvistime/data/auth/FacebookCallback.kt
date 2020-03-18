package com.bugmakers.jarvistime.data.auth

import com.facebook.FacebookCallback
import com.facebook.FacebookException

internal fun <T> getFacebookCallback(
    doOnSuccess: ((result: T) -> Unit)? = null,
    doOnError: ((error: FacebookException?) -> Unit)? = null,
    doOnCancel: (() -> Unit)? = null
) = object : FacebookCallback<T> {
    override fun onSuccess(result: T) {
        doOnSuccess?.invoke(result)
    }

    override fun onCancel() {
        doOnCancel?.invoke()
    }

    override fun onError(error: FacebookException?) {
        doOnError?.invoke(error)
    }
}