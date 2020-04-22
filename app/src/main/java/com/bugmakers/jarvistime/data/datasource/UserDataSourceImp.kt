package com.bugmakers.jarvistime.data.datasource

import com.bugmakers.jarvistime.data.local.kotpref.UserInfoPref
import com.bugmakers.jarvistime.domain.datasource.UserDataSource
import com.bugmakers.jarvistime.domain.entity.User
import io.reactivex.Completable
import io.reactivex.Single

internal class UserDataSourceImp(
    private val userInfoPref: UserInfoPref
) : UserDataSource {

    override fun updateUser(user: User): Completable =
        Completable.create {
            userInfoPref.username = user.username
            it.onComplete()
        }

    override fun updateToken(token: String): Completable =
        Completable.create {
            userInfoPref.token = token
            it.onComplete()
        }

    override fun isActiveUserExist(): Single<Boolean> =
        Single.create {
            val isUserExist = userInfoPref.username.isNotEmpty() && userInfoPref.token.isNotEmpty()
            it.onSuccess(isUserExist)
        }

    override fun getCurrentUser(): Single<User> =
        Single.create {
            val user = User(username = userInfoPref.username)
            it.onSuccess(user)
        }

    override fun getCurrentToken(): Single<String> =
        Single.create {
            val token = userInfoPref.token
            it.onSuccess(token)
        }

    override fun logOut(): Completable =
        Completable.create {
            userInfoPref.clean()

        }
}