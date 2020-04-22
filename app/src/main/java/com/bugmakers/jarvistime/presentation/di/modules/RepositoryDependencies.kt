package com.bugmakers.jarvistime.presentation.di.modules

import com.bugmakers.jarvistime.data.repository.AuthRepository
import com.bugmakers.jarvistime.data.repository.TaskRepository
import com.bugmakers.jarvistime.data.repository.UserRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

internal fun repositoryDependencies() = Kodein.Module("repositoryDependencies") {
    bind<AuthRepository>() with provider { AuthRepository(instance(), instance()) }
    bind<UserRepository>() with singleton { UserRepository(instance(), instance()) }
    bind<TaskRepository>() with singleton { TaskRepository(instance()) }
}