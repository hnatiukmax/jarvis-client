package com.bugmakers.jarvistime.presentation.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

internal infix fun CompositeDisposable.plus(disposable: Disposable) {
    this.add(disposable)
}