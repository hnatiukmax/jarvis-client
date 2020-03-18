package com.bugmakers.jarvistime.data.auth

import android.content.Context
import androidx.annotation.StringRes
import com.bugmakers.jarvistime.presentation.extensions.string
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

internal fun getGoogleSignInClient(
    context: Context,
    @StringRes clientId: Int
): GoogleSignInClient {
    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.string(clientId))
        .requestServerAuthCode(context.string(clientId))
        .requestEmail()
        .requestProfile()
        .build()
    return GoogleSignIn.getClient(context, signInOptions)
}