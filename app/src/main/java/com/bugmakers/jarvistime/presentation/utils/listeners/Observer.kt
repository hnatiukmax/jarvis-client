package com.bugmakers.jarvistime.presentation.utils.listeners

import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable

internal fun getCompletableObserver(
    doOnComplete: (() -> Unit)? = null,
    doOnSubscribe: ((d: Disposable) -> Unit)? = null,
    doOnError: ((e: Throwable) -> Unit)? = null
) = object : CompletableObserver {
    override fun onComplete() {
        doOnComplete?.invoke()
    }

    override fun onSubscribe(d: Disposable) {
        doOnSubscribe?.invoke(d)
    }

    override fun onError(e: Throwable) {
        doOnError?.invoke(e)
    }

}