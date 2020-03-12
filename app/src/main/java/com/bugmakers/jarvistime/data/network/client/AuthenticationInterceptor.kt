package com.bugmakers.jarvistime.data.network.client

import okhttp3.Interceptor
import okhttp3.Response

internal class AuthenticationInterceptor(
    override var xAccessToken: String? = null
) : Interceptor, Authenticator {

    override fun intercept(chain: Interceptor.Chain): Response {
        xAccessToken?.let {
            val isNeedAuthorize = chain.request().headers(AppHeaders.X_ACCESS_TOKEN_NAME).isNotEmpty()
            if (isNeedAuthorize) {
                val request = chain.request().newBuilder()
                    .header(AppHeaders.X_ACCESS_TOKEN_NAME, it)
                    .build()
                return chain.proceed(request)
            }
        }
        return chain.proceed(chain.request())
    }
}