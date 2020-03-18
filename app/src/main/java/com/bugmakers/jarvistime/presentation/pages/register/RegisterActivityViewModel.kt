package com.bugmakers.jarvistime.presentation.pages.register

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.bugmakers.jarvistime.data.repository.AuthRepository
import com.bugmakers.jarvistime.presentation.base.BaseViewModel
import com.bugmakers.jarvistime.presentation.extensions.AND
import com.bugmakers.jarvistime.presentation.extensions.plus
import com.bugmakers.jarvistime.presentation.utils.ActionLiveData
import com.bugmakers.jarvistime.presentation.utils.TypeUIMessage.ERROR
import com.bugmakers.jarvistime.presentation.utils.asStringResources
import com.bugmakers.jarvistime.presentation.utils.ifPasswordCheckValid
import com.bugmakers.jarvistime.presentation.utils.ifUsernameCheckValid
import com.bugmakers.jarvistime.presentation.utils.listeners.getDisposableCompletableObserver

internal class RegisterActivityViewModel(
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

        onCloseKeyboard.call()

        compositeDisposable plus authRepository.register(username.value ?: "", password.value ?: "")
            .enableProgress()
            .subscribeWith(getDisposableCompletableObserver(
                doOnComplete = {
                    onRegister.call()
                },
                doOnError = {
                    onShowMessage.value = ERROR to "Cannot access".asStringResources
                }
            ))
    }

    private fun isFieldsValid(): Boolean {
        usernameHasError.value = username.value.ifUsernameCheckValid()
        passwordHasError.value = password.value.ifPasswordCheckValid()
        confirmPasswordHasError.value = confirmPassword.value != password.value

        return !(usernameHasError.value ?: false) AND
                !(passwordHasError.value ?: false) AND
                !(confirmPasswordHasError.value ?: false)
    }
}