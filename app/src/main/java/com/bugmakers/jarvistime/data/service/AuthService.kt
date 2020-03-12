package com.bugmakers.jarvistime.data.service

import com.bugmakers.jarvistime.data.network.requests.UserRequestBody
import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthService {

    @POST("/signin")
    fun login(@Body loginBody : UserRequestBody) : Completable

    @POST("/signup")
    fun register(@Body registerBody : UserRequestBody) : Completable

    @POST("/sign_up_social")
    fun registerViaSocial(@Body registerBody: UserRequestBody) : Completable
}