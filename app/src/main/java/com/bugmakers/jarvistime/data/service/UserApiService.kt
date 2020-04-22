package com.bugmakers.jarvistime.data.service

import com.bugmakers.jarvistime.data.network.authorization.ApiHeaders
import com.bugmakers.jarvistime.data.network.response.UserInfoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

internal interface UserApiService {

    @Headers(ApiHeaders.AUTHORIZATION_TOKEN)
    @GET("/user/info")
    fun getUserInfo(): Single<UserInfoResponse>
}