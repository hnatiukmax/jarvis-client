package com.bugmakers.jarvistime.data.auth

import com.facebook.CallbackManager

internal class FacebookAuthService : SocialAuthService {

    private lateinit var facebookCallbackManager: CallbackManager

    override fun initializeService() {
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Any): String {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }
}