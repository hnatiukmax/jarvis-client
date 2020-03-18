package com.bugmakers.jarvistime.presentation.pages.introduction

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.bugmakers.jarvistime.data.auth.getFacebookCallback
import com.bugmakers.jarvistime.data.repository.AuthRepository
import com.bugmakers.jarvistime.domain.entity.AuthenticationType
import com.bugmakers.jarvistime.presentation.base.BaseViewModel
import com.bugmakers.jarvistime.presentation.extensions.plus
import com.bugmakers.jarvistime.presentation.utils.ActionLiveData
import com.bugmakers.jarvistime.presentation.utils.TypeUIMessage.ERROR
import com.bugmakers.jarvistime.presentation.utils.asStringResources
import com.bugmakers.jarvistime.presentation.utils.listeners.getCompletableObserver
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
    val onAuth = MutableLiveData<AuthenticationType>()

    val authGoogleIntent : Intent
        get() = authGoogleClient.signInIntent

    fun onSocialAuthenticationClick(type: AuthenticationType) {
        onAuth.value = type
    }

    init {
        initFacebookClient()
    }

    fun onFacebookAuthResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data?.let {
            facebookCallbackManager.onActivityResult(requestCode, resultCode, data)
        } ?: run {
            onShowMessage.value = ERROR to "Facebook authentication failed".asStringResources
        }
    }

    fun onGoogleAuthResult(data: Intent?) {
        if (data == null) {
            onShowMessage.value = ERROR to "Google authentication failed".asStringResources
            return
        }

        val completedTask = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account = completedTask.getResult(ApiException::class.java)
            account?.idToken?.let {
                register(AuthenticationType.GOOGLE, it)
            } ?: run {
                onShowMessage.value = ERROR to "Google authentication failed".asStringResources
            }

        } catch (e: ApiException) {
            onShowMessage.value = ERROR to e.localizedMessage.asStringResources
        }
    }

    private fun initFacebookClient() {
        facebookCallbackManager = CallbackManager.Factory.create()

        val callback = getFacebookCallback<LoginResult>(
            doOnSuccess = {
                val accessToken = AccessToken.getCurrentAccessToken()
                if (accessToken != null && !accessToken.isExpired) {
                    register(AuthenticationType.FACEBOOK, accessToken.token)
                }
            },
            doOnError = {
                if (it is FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().apply {
                            logOut()
                            onAuth.value = AuthenticationType.FACEBOOK
                        }
                    }
                }
            }
        )

        LoginManager.getInstance().registerCallback(facebookCallbackManager, callback)
    }

    @SuppressLint("CheckResult")
    private fun register(authType: AuthenticationType, token: String) {
        compositeDisposable plus authRepository.registerViaSocial(authType, token)
            .enableProgress()
            .subscribeWith(getDisposableCompletableObserver(
                doOnComplete = {
                    onRegisterCompleted.call()
                },
                doOnError = {
                    onShowMessage.value = ERROR to "Cannot access".asStringResources
                }
            ))
    }
}