package com.bugmakers.jarvistime.data.service

import com.bugmakers.jarvistime.data.network.requests.SocialAuthRequestBody
import com.bugmakers.jarvistime.data.network.requests.UserRequestBody
import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthService {

    @POST("/login")
    fun login(@Body loginBody : UserRequestBody) : Completable

    @POST("/register")
    fun register(@Body registerBody : UserRequestBody) : Completable

    @POST("/register-social")
    fun registerViaSocial(@Body socialAuthRequestBody: SocialAuthRequestBody) : Completable
}