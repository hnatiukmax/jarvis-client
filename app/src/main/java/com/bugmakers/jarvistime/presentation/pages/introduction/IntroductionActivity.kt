package com.bugmakers.jarvistime.presentation.pages.introduction

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityIntroductionBinding
import com.bugmakers.jarvistime.presentation.extensions.showToast
import com.bugmakers.jarvistime.presentation.utils.listeners.SimpleFacebookCallback
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

class IntroductionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroductionBinding
    private val viewModel = IntroductionActivityViewModel()

    private lateinit var facebookCallbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_introduction)
        binding.apply {
            lifecycleOwner = this@IntroductionActivity
            viewModel = this@IntroductionActivity.viewModel
        }

        viewModel.apply {
            onGoogleAuth.observe(this@IntroductionActivity, Observer {
                showToast("Google click")
            })
            onFacebookAuth.observe(this@IntroductionActivity, Observer {
                LoginManager.getInstance()
                    .logInWithReadPermissions(this@IntroductionActivity, listOf("public_profile"))
            })
        }

        initFacebookClient()
    }

    private fun initFacebookClient() {
        facebookCallbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(facebookCallbackManager,
            object : SimpleFacebookCallback<LoginResult>() {
                override fun onSuccess(result: LoginResult) {
                    val accessToken = AccessToken.getCurrentAccessToken()
                    if (accessToken != null && !accessToken.isExpired) {
                        showToast("Success Login")
                    }
                }

                override fun onError(error: FacebookException) {
                    showToast("Error Login")
                }

                override fun onCancel() {
                    showToast("Cancel")
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
