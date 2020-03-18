package com.bugmakers.jarvistime.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bugmakers.jarvistime.presentation.utils.ActionLiveData
import com.bugmakers.jarvistime.presentation.utils.StringResource
import com.bugmakers.jarvistime.presentation.utils.TypeUIMessage
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable

internal abstract class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    /**
     * Variables, that will be observed by View.
     */
    val isProgressVisible = MutableLiveData<Boolean>()
    val onCloseKeyboard = ActionLiveData<Unit>()
    val onShowMessage = MutableLiveData<Pair<TypeUIMessage, StringResource>>()

    /**
     * Will be called, when View will be destroyed.
     */
    public override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    protected fun Completable.enableProgress(): Completable =
        doOnSubscribe { isProgressVisible.value = true }
        .doFinally { isProgressVisible.value = false }
}