package com.bugmakers.jarvistime.presentation.pages.authentication.register

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.bugmakers.jarvistime.data.repository.AuthRepository
import com.bugmakers.jarvistime.presentation.base.BaseViewModel
import com.bugmakers.jarvistime.presentation.extensions.isRight
import com.bugmakers.jarvistime.presentation.extensions.plus
import com.bugmakers.jarvistime.presentation.extensions.valueOrEmpty
import com.bugmakers.jarvistime.presentation.utils.base.ActionLiveData
import com.bugmakers.jarvistime.presentation.utils.ifPasswordCheckValid
import com.bugmakers.jarvistime.presentation.utils.ifUsernameCheckValid
import com.bugmakers.jarvistime.presentation.utils.rxjava.getDisposableCompletableObserver
import com.bugmakers.jarvistime.presentation.utils.rxjava.subscribe

internal class RegisterFragmentViewModel(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    val onRegister = ActionLiveData<Unit>()

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    val usernameHasError = MutableLiveData<Boolean>()
    val passwordHasError = MutableLiveData<Boolean>()
    val confirmPasswordHasError = MutableLiveData<Boolean>()

    @SuppressLint("CheckResult")
    fun onRegisterClick() {
        if (!isFieldsValid()) {
            return
        }

        compositeDisposable plus authRepository.register(username.valueOrEmpty, password.valueOrEmpty)
            .enableProgress()
            .onCloseKeyboard()
            .handleError()
            .subscribe(
                onComplete = { onRegister.call() }
            )
    }

    private fun isFieldsValid(): Boolean {
        usernameHasError.value = !username.value.ifUsernameCheckValid()
        passwordHasError.value = !password.value.ifPasswordCheckValid()
        confirmPasswordHasError.value =
            passwordHasError.isRight or (confirmPassword.value != password.value)

        return true
//        return !usernameHasError.isRight AND !passwordHasError.isRight AND !confirmPasswordHasError.isRight
    }
}