package com.bugmakers.jarvistime.data.repository

import com.bugmakers.jarvistime.data.network.response.toDomainEntity
import com.bugmakers.jarvistime.data.service.UserApiService
import com.bugmakers.jarvistime.domain.datasource.UserDataSource
import com.bugmakers.jarvistime.domain.entity.UserInfo
import com.bugmakers.jarvistime.presentation.common.rxjava.multiThreads
import io.reactivex.Single

internal class UserRepository(
    private val userDataSource: UserDataSource,
    private val userApiService: UserApiService
) {

    fun getUserInfo(): Single<UserInfo> {
        return userApiService.getUserInfo()
            .multiThreads()
            .map { it.toDomainEntity() }
    }
}