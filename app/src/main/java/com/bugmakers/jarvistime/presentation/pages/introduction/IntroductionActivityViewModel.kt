package com.bugmakers.jarvistime.presentation.pages.introduction

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.data.auth.getFacebookCallback
import com.bugmakers.jarvistime.data.repository.AuthRepository
import com.bugmakers.jarvistime.domain.entity.AuthorizationType
import com.bugmakers.jarvistime.presentation.base.BaseViewModel
import com.bugmakers.jarvistime.presentation.entity.AppUIMessage
import com.bugmakers.jarvistime.presentation.extensions.plus
import com.bugmakers.jarvistime.presentation.utils.ActionLiveData
import com.bugmakers.jarvistime.presentation.entity.enums.TypeUIMessage.ERROR
import com.bugmakers.jarvistime.presentation.entity.enums.TypeUIMessage.INFORM
import com.bugmakers.jarvistime.presentation.utils.asStringResources
import com.bugmakers.jarvistime.presentation.utils.listeners.getDisposableCompletableObserver
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookAuthorizationException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException

internal class IntroductionActivityViewModel(
    private val authRepository: AuthRepository,
    private val authGoogleClient: GoogleSignInClient
) : BaseViewModel() {

    private lateinit var facebookCallbackManager: CallbackManager

    val onRegisterCompleted = ActionLiveData<Unit>()
    val onAuth = MutableLiveData<AuthorizationType>()

    val authGoogleIntent: Intent
        get() = authGoogleClient.signInIntent

    init {
        initFacebookClient()
    }

    fun onFacebookAuthResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data?.let {
            facebookCallbackManager.onActivityResult(requestCode, resultCode, data)
        } ?: run {
            onShowMessage.value = AppUIMessage(ERROR, R.string.error_facebook_auth_failed.asStringResources)
        }
    }

    fun onGoogleAuthResult(data: Intent?) {
        val googleAuthError = AppUIMessage(ERROR, R.string.error_google_auth_failed.asStringResources)

        if (data == null) {
            onShowMessage.value = googleAuthError
            return
        }

        val completedTask = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account = completedTask.getResult(ApiException::class.java)
            account?.idToken?.let {
                register(AuthorizationType.GOOGLE, it)
            } ?: run {
                onShowMessage.value = googleAuthError
            }

        } catch (e: ApiException) {
            onShowMessage.value = AppUIMessage(ERROR, e.localizedMessage.asStringResources)
        }
    }

    private fun initFacebookClient() {
        facebookCallbackManager = CallbackManager.Factory.create()

        val callback = getFacebookCallback<LoginResult>(
            doOnSuccess = {
                val accessToken = AccessToken.getCurrentAccessToken()
                if (accessToken != null && !accessToken.isExpired) {
                    register(AuthorizationType.FACEBOOK, accessToken.token)
                }
            },
            doOnError = {
                if (it is FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().apply {
                            logOut()
                            onAuth.value = AuthorizationType.FACEBOOK
                        }
                    }
                }
            }
        )

        LoginManager.getInstance().registerCallback(facebookCallbackManager, callback)
    }

    @SuppressLint("CheckResult")
    private fun register(authType: AuthorizationType, token: String) {
        compositeDisposable plus authRepository.registerViaSocial(authType, token)
            .enableProgress()
            .handleError()
            .subscribeWith(getDisposableCompletableObserver(
                doOnComplete = {
                    onShowMessage.value = AppUIMessage(INFORM, R.string.login_success.asStringResources)
                    onRegisterCompleted.call()
                }
            ))
    }

    fun onSocialAuthenticationClick(type: AuthorizationType) {
        onAuth.value = type
    }
}