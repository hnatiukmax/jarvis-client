package com.bugmakers.jarvistime.presentation.pages.login

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.bugmakers.jarvistime.data.repository.AuthRepository
import com.bugmakers.jarvistime.presentation.base.BaseViewModel
import com.bugmakers.jarvistime.presentation.utils.ActionLiveData
import com.bugmakers.jarvistime.presentation.utils.listeners.getCompletableObserver

internal class LogInActivityViewModel(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    val onLogin = ActionLiveData<Unit>()

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    @SuppressLint("CheckResult")
    fun onLogInClick() {
        if (isFieldsValid()) {
            authRepository.login(username.value ?: "", password.value ?: "")
                .doFinally { isProgressVisible.value = false }
                .subscribeWith(getCompletableObserver(
                    doOnComplete = {
                        onLogin.call()
                    },
                    doOnError = {
                        onOpenMessageDialog.value = Pair("Cannot access", false)
                    },
                    doOnSubscribe = {
                        isProgressVisible.value = true
                    }
                ))
        }
    }

    private fun isFieldsValid() : Boolean {
        return if (!username.value.isNullOrEmpty() && !password.value.isNullOrEmpty()) {
            true
        } else {
            onOpenMessageDialog.value = Pair("Input data is invalid", false)
            false
        }
    }

}