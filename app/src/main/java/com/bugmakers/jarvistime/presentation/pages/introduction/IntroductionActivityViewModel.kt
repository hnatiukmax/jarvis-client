package com.bugmakers.jarvistime.presentation.pages.introduction

import com.bugmakers.jarvistime.domain.entity.AuthenticationType
import com.bugmakers.jarvistime.presentation.base.BaseViewModel
import com.bugmakers.jarvistime.presentation.utils.ActionLiveData

internal class IntroductionActivityViewModel : BaseViewModel() {

    val onFacebookAuth = ActionLiveData<Unit>()
    val onGoogleAuth = ActionLiveData<Unit>()

    fun onSocialAuthenticationClick(type : AuthenticationType) {
        when (type) {
            AuthenticationType.FACEBOOK -> onFacebookAuth.call()
            AuthenticationType.GOOGLE -> onGoogleAuth.call()

        }
    }
}