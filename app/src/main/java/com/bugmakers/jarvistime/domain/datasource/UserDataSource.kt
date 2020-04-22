package com.bugmakers.jarvistime.domain.datasource

import com.bugmakers.jarvistime.domain.entity.User
import io.reactivex.Completable
import io.reactivex.Single

internal interface UserDataSource {

    fun updateUser(user: User) : Completable

    fun updateToken(token: String) : Completable

    fun isActiveUserExist() : Single<Boolean>

    fun getCurrentUser() : Single<User>

    fun getCurrentToken() : Single<String>

    fun logOut() : Completable
}