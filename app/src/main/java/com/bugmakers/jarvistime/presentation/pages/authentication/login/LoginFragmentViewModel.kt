package com.bugmakers.jarvistime.presentation.pages.authentication.login

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.bugmakers.jarvistime.data.repository.AuthRepository
import com.bugmakers.jarvistime.presentation.base.BaseViewModel
import com.bugmakers.jarvistime.presentation.extensions.isRight
import com.bugmakers.jarvistime.presentation.extensions.plus
import com.bugmakers.jarvistime.presentation.extensions.valueOrEmpty
import com.bugmakers.jarvistime.presentation.common.base.ActionLiveData
import com.bugmakers.jarvistime.presentation.common.rxjava.*

internal class LoginFragmentViewModel(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    val onLogin = ActionLiveData<Unit>()

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val usernameHasError = MutableLiveData<Boolean>()
    val passwordHasError = MutableLiveData<Boolean>()

    @SuppressLint("CheckResult")
    fun onLogInClick() {
        if (!isFieldsValid()) {
            return
        }

        compositeDisposable plus authRepository.login(username.valueOrEmpty, password.valueOrEmpty)
            .enableProgress()
            .onCloseKeyboard()
            .handleError()
            .subscribe(
                onSuccess = { onLogin.call() }
            )
    }

    private fun isFieldsValid(): Boolean {
        usernameHasError.value = username.value.isNullOrEmpty()
        passwordHasError.value = password.value.isNullOrEmpty()

        return !usernameHasError.isRight and !passwordHasError.isRight
    }
}