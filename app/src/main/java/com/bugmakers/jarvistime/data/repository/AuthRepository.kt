package com.bugmakers.jarvistime.data.repository

import com.bugmakers.jarvistime.data.network.requests.SocialAuthRequestBody
import com.bugmakers.jarvistime.data.network.requests.UserRequestBody
import com.bugmakers.jarvistime.data.service.AuthService
import com.bugmakers.jarvistime.domain.entity.AuthorizationType
import com.bugmakers.jarvistime.domain.entity.toDataEntity
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal class AuthRepository(
    private val authService: AuthService
) {

    fun login(username: String, password: String): Completable {
        return authService.login(UserRequestBody(username, password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun register(username: String, password: String): Completable {
        return authService.register(UserRequestBody(username, password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun registerViaSocial(authType: AuthorizationType, token: String): Completable {
        return authService.registerViaSocial(SocialAuthRequestBody(authType.toDataEntity(), token))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}