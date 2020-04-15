package com.bugmakers.jarvistime.presentation.di.modules

import com.bugmakers.jarvistime.BuildConfig
import org.kodein.di.Kodein
import org.kodein.di.generic.with

enum class AppConstant {
    BASE_URL,
    DATABASE_PREFIX
}

internal fun applicationIdentifiersDependencies() = Kodein.Module("identifiersModule") {
    constant(AppConstant.BASE_URL) with BuildConfig.API_URL
    constant(AppConstant.DATABASE_PREFIX) with ""
}