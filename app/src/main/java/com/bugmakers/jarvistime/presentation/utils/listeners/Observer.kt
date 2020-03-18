package com.bugmakers.jarvistime.presentation.utils.listeners

import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver

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

internal fun getDisposableCompletableObserver(
    doOnComplete: (() -> Unit)? = null,
    doOnError: ((e: Throwable) -> Unit)? = null
) = object : DisposableCompletableObserver() {
    override fun onComplete() {
        doOnComplete?.invoke()
    }

    override fun onError(e: Throwable) {
        doOnError?.invoke(e)
    }

}