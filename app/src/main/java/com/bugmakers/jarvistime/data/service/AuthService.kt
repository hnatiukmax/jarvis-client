package com.bugmakers.jarvistime.data.service

import com.bugmakers.jarvistime.data.network.requests.SocialAuthRequestBody
import com.bugmakers.jarvistime.data.network.requests.UserRequestBody
import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthService {

    @POST("/user/login")
    fun login(@Body loginBody: UserRequestBody): Completable

    @POST("/user/register")
    fun register(@Body registerBody: UserRequestBody): Completable

    @POST("/user/auth-by-social")
    fun registerViaSocial(@Body socialAuthRequestBody: SocialAuthRequestBody): Completable
}