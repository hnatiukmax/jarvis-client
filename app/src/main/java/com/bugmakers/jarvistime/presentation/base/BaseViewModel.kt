package com.bugmakers.jarvistime.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bugmakers.jarvistime.presentation.entity.AppException
import com.bugmakers.jarvistime.presentation.entity.AppUIMessage
import com.bugmakers.jarvistime.presentation.entity.appUIMessage
import com.bugmakers.jarvistime.presentation.extensions.disable
import com.bugmakers.jarvistime.presentation.extensions.enable
import com.bugmakers.jarvistime.presentation.common.base.ActionLiveData
import com.bugmakers.jarvistime.presentation.common.rxjava.doOnError
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

internal abstract class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    /**
     * Variables, that will be observed by View.
     */
    val isProgressVisible = MutableLiveData<Boolean>()
    val onCloseKeyboard = ActionLiveData<Unit>()
    val onShowMessage = MutableLiveData<AppUIMessage>()

    /**
     * Will be called, when View will be destroyed.
     */
    public override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    protected fun Completable.enableProgress() = this.apply {
        return doOnSubscribe { isProgressVisible.enable() }
            .doFinally { isProgressVisible.disable() }
    }

    protected fun <T> Observable<T>.enableProgress() = this.apply {
        return doOnSubscribe { isProgressVisible.enable() }
            .doFinally { isProgressVisible.disable() }
    }

    protected fun <T> Single<T>.enableProgress() = this.apply {
        return doOnSubscribe { isProgressVisible.enable() }
            .doFinally { isProgressVisible.disable() }
    }

    protected fun <T> Flowable<T>.enableProgress() = this.apply {
        return doOnSubscribe { isProgressVisible.enable() }
            .doFinally { isProgressVisible.disable() }
    }

    protected fun Completable.handleError() = this.apply {
        return doOnError { error: AppException ->
            onShowMessage.value = error.appUIMessage
        }
    }

    protected fun <T> Observable<T>.handleError() = this.apply {
        return doOnError { error: AppException ->
            onShowMessage.value = error.appUIMessage
        }
    }

    protected fun <T> Single<T>.handleError() = this.apply {
         return doOnError { error: AppException ->
            onShowMessage.value = error.appUIMessage
        }
    }

    protected fun Completable.onCloseKeyboard()  = this.apply {
        onCloseKeyboard.call()
    }

    protected fun <T> Observable<T>.onCloseKeyboard()  = this.apply {
        onCloseKeyboard.call()
    }

    protected fun <T> Single<T>.onCloseKeyboard() = this.apply {
        onCloseKeyboard.call()
    }

    protected fun <T> Flowable<T>.onCloseKeyboard() = this.apply {
        onCloseKeyboard.call()
    }
}