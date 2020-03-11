package com.bugmakers.jarvistime.presentation.utils.listeners

import com.facebook.FacebookCallback
import com.facebook.FacebookException

internal open class SimpleFacebookCallback<R>: FacebookCallback<R> {

    override fun onSuccess(result: R) {}

    override fun onCancel() {}

    override fun onError(error: FacebookException) {}
}