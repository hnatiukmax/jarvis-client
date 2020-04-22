package com.bugmakers.jarvistime.presentation.di.modules

import com.bugmakers.jarvistime.data.network.client.RestClient
import com.bugmakers.jarvistime.data.network.client.authService
import com.bugmakers.jarvistime.data.network.client.taskService
import com.bugmakers.jarvistime.data.network.client.userService
import com.bugmakers.jarvistime.data.service.AuthApiService
import com.bugmakers.jarvistime.data.service.TaskApiService
import com.bugmakers.jarvistime.data.service.UserApiService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal fun apiServiceDependencies() = Kodein.Module("apiServiceDependencies") {
    bind<AuthApiService>() with singleton { instance<RestClient>().authService }
    bind<TaskApiService>() with singleton { instance<RestClient>().taskService }
    bind<UserApiService>() with singleton { instance<RestClient>().userService }
}