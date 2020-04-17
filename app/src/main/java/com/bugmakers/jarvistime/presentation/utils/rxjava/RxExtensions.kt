package com.bugmakers.jarvistime.presentation.utils.rxjava

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import io.reactivex.internal.observers.CallbackCompletableObserver
import io.reactivex.internal.observers.ConsumerSingleObserver
import io.reactivex.internal.observers.LambdaObserver

internal fun Completable.subscribe(
    onComplete: () -> Unit,
    onError: ((Throwable) -> Unit)? = null
): Disposable {
    val onCompleteAction = Action { onComplete() }
    val onErrorAction = Consumer<Throwable> { throwable -> onError?.invoke(throwable) }

    val callback = CallbackCompletableObserver(onErrorAction, onCompleteAction)
    
    subscribe(callback)
    return callback
}

internal fun <T> Observable<T>.subscribe(
    onNext: (T) -> Unit,
    onError: ((Throwable) -> Unit)? = null,
    onComplete: (() -> Unit)? = null
) : Disposable {
    val onNextAction = Consumer<T> { onNext(it) }
    val onErrorAction = Consumer<Throwable> { throwable -> onError?.invoke(throwable) }
    val onCompleteAction = Action { onComplete?.invoke() }

    val callback = LambdaObserver(onNextAction, onErrorAction, onCompleteAction, Functions.emptyConsumer())

    subscribe(callback)
    return callback
}

internal fun <T> Single<T>.subscribe(
    onSuccess: (T) -> Unit,
    onError: ((Throwable) -> Unit)? = null
) : Disposable {
    val onSuccessAction = Consumer(onSuccess)
    val onErrorAction = Consumer<Throwable> { throwable -> onError?.invoke(throwable) }

    val callback = ConsumerSingleObserver(onSuccessAction, onErrorAction)

    subscribe(callback)
    return callback
}