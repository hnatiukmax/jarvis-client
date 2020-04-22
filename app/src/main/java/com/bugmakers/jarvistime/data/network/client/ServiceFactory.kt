package com.bugmakers.jarvistime.data.network.client

import com.bugmakers.jarvistime.data.service.AuthApiService
import com.bugmakers.jarvistime.data.service.TaskApiService
import com.bugmakers.jarvistime.data.service.UserApiService

internal val RestClient.authService
    get() = retrofit.create(AuthApiService::class.java)

internal val RestClient.taskService
    get() = retrofit.create(TaskApiService::class.java)

internal val RestClient.userService
    get() = retrofit.create(UserApiService::class.java)