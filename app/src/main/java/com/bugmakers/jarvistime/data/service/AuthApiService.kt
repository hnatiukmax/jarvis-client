package com.bugmakers.jarvistime.data.service

import com.bugmakers.jarvistime.data.network.requests.SocialAuthRequestBody
import com.bugmakers.jarvistime.data.network.requests.UserRequestBody
import com.bugmakers.jarvistime.data.network.response.AuthResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthApiService {

    @POST("/user/login")
    fun login(@Body loginBody: UserRequestBody): Single<AuthResponse>

    @POST("/user/register")
    fun register(@Body registerBody: UserRequestBody): Single<AuthResponse>

    @POST("/user/auth-by-social")
    fun registerViaSocial(@Body socialAuthRequestBody: SocialAuthRequestBody): Single<AuthResponse>
}