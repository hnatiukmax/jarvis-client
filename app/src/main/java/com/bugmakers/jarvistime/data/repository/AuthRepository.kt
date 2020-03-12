package com.bugmakers.jarvistime.data.repository

import com.bugmakers.jarvistime.data.network.requests.UserRequestBody
import com.bugmakers.jarvistime.data.service.AuthService
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal class AuthRepository(
    private val authService: AuthService
) {

    fun login(username: String, password: String): Completable {
        return authService.login(
            UserRequestBody(
                username = username,
                password = password
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun register(username: String, password: String): Completable {
        return authService.register(
            UserRequestBody(
                username = username,
                password = password
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun registerViaSocial(username: String, password: String): Completable {
        return authService.registerViaSocial(
            UserRequestBody(
                username = username,
                password = password
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}