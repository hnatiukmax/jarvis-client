package com.bugmakers.jarvistime.data.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.presentation.extensions.AND
import com.bugmakers.jarvistime.presentation.extensions.string
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

internal class GoogleAuthService(
    private val context: Context
) : SocialAuthService {

    companion object {
        private const val REQUEST_CODE_GOOGLE_SIGN_IN = 1101
    }

    private lateinit var authClient: GoogleSignInClient

    val authIntent: Intent
        get() = authClient.signInIntent

    override fun initializeService() {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.string(R.string.server_client_id))
            .requestServerAuthCode(context.string(R.string.server_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        authClient = GoogleSignIn.getClient(context, signInOptions)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Any): String {
        if ((data !is Intent) AND (requestCode != REQUEST_CODE_GOOGLE_SIGN_IN) AND (resultCode != Activity.RESULT_OK)) {
            throw Exception("Cannot auth via Google")
        }

        val completedTask = GoogleSignIn.getSignedInAccountFromIntent(data as Intent)

        try {
            val account = completedTask.getResult(ApiException::class.java)
            account?.idToken?.let {
                return it
            } ?: throw Exception("Cannot auth via Google")

        } catch (e: ApiException) {
            throw e
        }
    }

    override fun signOut() {
        authClient.signOut()
    }
}