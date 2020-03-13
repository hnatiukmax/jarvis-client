package com.bugmakers.jarvistime.presentation.pages.register

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.bugmakers.jarvistime.data.repository.AuthRepository
import com.bugmakers.jarvistime.presentation.base.BaseViewModel
import com.bugmakers.jarvistime.presentation.utils.ActionLiveData
import com.bugmakers.jarvistime.presentation.utils.listeners.getCompletableObserver

internal class RegisterActivityViewModel(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    val onRegister = ActionLiveData<Unit>()

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    @SuppressLint("CheckResult")
    fun onRegisterClick() {
        if (!isFieldsValid()) {
            return
        }

        authRepository.register(username.value ?: "", password.value ?: "")
            .doFinally { isProgressVisible.value = false }
            .subscribeWith(getCompletableObserver(
                doOnComplete = {
                    onRegister.call()
                },
                doOnError = {
                    onOpenMessageDialog.value = Pair("Cannot access", false)
                },
                doOnSubscribe = {
                    isProgressVisible.value = true
                }
            ))
    }

    private fun isFieldsValid(): Boolean {
        return if (!username.value.isNullOrEmpty() && !password.value.isNullOrEmpty() && password.value == confirmPassword.value) {
            true
        } else {
            onOpenMessageDialog.value = Pair("Input data is invalid", false)
            false
        }
    }
}