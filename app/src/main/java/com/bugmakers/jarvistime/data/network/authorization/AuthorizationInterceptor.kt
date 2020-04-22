package com.bugmakers.jarvistime.data.network.authorization

import android.annotation.SuppressLint
import com.bugmakers.jarvistime.data.network.authorization.ApiHeaders.Companion.AUTHORIZATION_HEADER
import com.bugmakers.jarvistime.domain.datasource.UserDataSource
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthorizationInterceptor(
    private val userDataSource: UserDataSource
) : Interceptor {

    @SuppressLint("CheckResult")
    override fun intercept(chain: Interceptor.Chain): Response {
        val isNeedToSetAuthorization = chain.request().headers(AUTHORIZATION_HEADER).isNotEmpty()
        if (isNeedToSetAuthorization) {
            var mToken = ""
            userDataSource.getCurrentToken().subscribe { token ->
                mToken = token
            }

            val request = chain.request().newBuilder()
                .header(AUTHORIZATION_HEADER, mToken)
                .build()

            return chain.proceed(request)
        }
        return chain.proceed(chain.request())
    }
}