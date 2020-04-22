package com.bugmakers.jarvistime.data.repository

import com.bugmakers.jarvistime.data.network.requests.SocialAuthRequestBody
import com.bugmakers.jarvistime.data.network.requests.UserRequestBody
import com.bugmakers.jarvistime.data.network.response.AuthResponse
import com.bugmakers.jarvistime.data.service.AuthApiService
import com.bugmakers.jarvistime.domain.datasource.UserDataSource
import com.bugmakers.jarvistime.domain.entity.AuthorizationType
import com.bugmakers.jarvistime.domain.entity.User
import com.bugmakers.jarvistime.domain.entity.toDataEntity
import com.bugmakers.jarvistime.presentation.common.rxjava.multiThreads
import io.reactivex.Single

internal class AuthRepository(
    private val authApiService: AuthApiService,
    private val userDataSource: UserDataSource
) {

    fun login(username: String, password: String): Single<AuthResponse> {
        return authApiService.login(UserRequestBody(username, password))
            .multiThreads()
            .doOnSuccess { saveUser(it) }
    }

    fun register(username: String, password: String): Single<AuthResponse> {
        return authApiService.register(UserRequestBody(username, password))
            .multiThreads()
            .doOnSuccess { saveUser(it) }
    }

    fun registerViaSocial(authType: AuthorizationType, token: String): Single<AuthResponse> {
        return authApiService.registerViaSocial(SocialAuthRequestBody(authType.toDataEntity(), token))
            .multiThreads()
            .doOnSuccess { saveUser(it) }
    }

    private fun saveUser(authResponse: AuthResponse) {
        userDataSource.apply {
            updateToken(authResponse.token).subscribe()
            updateUser(User(
                username = authResponse.username
            )).subscribe()
        }
    }
}