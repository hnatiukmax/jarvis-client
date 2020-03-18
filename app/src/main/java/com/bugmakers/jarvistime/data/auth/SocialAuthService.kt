package com.bugmakers.jarvistime.data.auth

internal interface SocialAuthService {

    fun initializeService()

    fun onResult(requestCode : Int = 0, resultCode : Int = 0, data : Any) : String

    fun signOut()
}