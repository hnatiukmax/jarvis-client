package com.bugmakers.jarvistime.presentation.di.modules

import com.bugmakers.jarvistime.data.repository.AuthRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

internal fun repositoryDependencies() = Kodein.Module("repositoryDependencies") {
    bind<AuthRepository>() with provider { AuthRepository(instance()) }
}