package com.bugmakers.jarvistime.presentation.pages.introduction

import android.annotation.SuppressLint
import com.bugmakers.jarvistime.data.repository.AuthRepository
import com.bugmakers.jarvistime.domain.entity.AuthenticationType
import com.bugmakers.jarvistime.presentation.base.BaseViewModel
import com.bugmakers.jarvistime.presentation.utils.ActionLiveData
import com.bugmakers.jarvistime.presentation.utils.listeners.getCompletableObserver

internal class IntroductionActivityViewModel(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    val onRegister = ActionLiveData<Unit>()

    val onFacebookAuth = ActionLiveData<Unit>()
    val onGoogleAuth = ActionLiveData<Unit>()
    val onEmailAuth = ActionLiveData<Unit>()

    fun onSocialAuthenticationClick(type: AuthenticationType) {
        when (type) {
            AuthenticationType.FACEBOOK -> onFacebookAuth.call()
            AuthenticationType.GOOGLE -> onGoogleAuth.call()
            AuthenticationType.EMAIL -> onEmailAuth.call()
        }
    }

    @SuppressLint("CheckResult")
    private fun register(login: String?, password: String?) {
        if (!isFieldsValid(login, password)) {
            return
        }

        authRepository.registerViaSocial(login ?: "", password ?: "")
            .doFinally { isProgressVisible.value = true }
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

    private fun isFieldsValid(login: String?, password: String?): Boolean {
        return !login.isNullOrEmpty() && !password.isNullOrEmpty()
    }
}