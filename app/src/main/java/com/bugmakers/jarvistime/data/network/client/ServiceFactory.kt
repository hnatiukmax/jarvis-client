package com.bugmakers.jarvistime.data.network.client

import com.bugmakers.jarvistime.data.service.AuthService
import com.bugmakers.jarvistime.data.service.TaskService

internal val RestClient.authService
    get() =  retrofit.create(AuthService::class.java)

internal val RestClient.taskService
    get() =  retrofit.create(TaskService::class.java)