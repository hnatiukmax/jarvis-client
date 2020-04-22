package com.bugmakers.jarvistime.presentation.di.modules

import com.bugmakers.jarvistime.data.datasource.UserDataSourceImp
import com.bugmakers.jarvistime.data.local.kotpref.UserInfoPref
import com.bugmakers.jarvistime.domain.datasource.UserDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal fun dataSourceDependencies() = Kodein.Module("dataSourceDependencies") {
    bind<UserInfoPref>() with singleton { UserInfoPref() }
    bind<UserDataSource>() with singleton { UserDataSourceImp(instance()) }
}