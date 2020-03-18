package com.bugmakers.jarvistime.presentation.pages.login

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.bugmakers.jarvistime.data.repository.AuthRepository
import com.bugmakers.jarvistime.presentation.base.BaseViewModel
import com.bugmakers.jarvistime.presentation.extensions.AND
import com.bugmakers.jarvistime.presentation.extensions.plus
import com.bugmakers.jarvistime.presentation.utils.ActionLiveData
import com.bugmakers.jarvistime.presentation.utils.TypeUIMessage.ERROR
import com.bugmakers.jarvistime.presentation.utils.asStringResources
import com.bugmakers.jarvistime.presentation.utils.listeners.getDisposableCompletableObserver

internal class LogInActivityViewModel(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    val onLogin = ActionLiveData<Unit>()

    val usernameHasError = MutableLiveData<Boolean>()
    val passwordHasError = MutableLiveData<Boolean>()

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    @SuppressLint("CheckResult")
    fun onLogInClick() {
        if (!isFieldsValid()) {
            return
        }

        onCloseKeyboard.call()

        compositeDisposable plus authRepository.login(
                username = username.value ?: "",
                password = password.value ?: ""
            )
            .enableProgress()
            .subscribeWith(getDisposableCompletableObserver(
                doOnComplete = {
                    onLogin.call()
                },
                doOnError = {
                    onShowMessage.value = ERROR to "Cannot access".asStringResources
                }
            ))
    }

    private fun isFieldsValid(): Boolean {
        usernameHasError.value = username.value.isNullOrEmpty()
        passwordHasError.value = password.value.isNullOrEmpty()

        return !(usernameHasError.value ?: false) AND !(passwordHasError.value ?: false)
    }
}