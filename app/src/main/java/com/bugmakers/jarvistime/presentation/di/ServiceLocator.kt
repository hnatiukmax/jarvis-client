package com.bugmakers.jarvistime.presentation.di

import android.app.Application
import com.bugmakers.jarvistime.presentation.di.modules.applicationIdentifiersDependencies
import com.bugmakers.jarvistime.presentation.di.modules.networkDependencies
import com.bugmakers.jarvistime.presentation.di.modules.repositoryDependencies
import com.bugmakers.jarvistime.presentation.di.modules.viewModelDependencies
import org.kodein.di.Kodein
import org.kodein.di.conf.ConfigurableKodein

class ServiceLocator(
    private val kodein: ConfigurableKodein,
    private val app: Application
) {

    fun setInjection() {
        kodein.clear()
        addModule(applicationIdentifiersDependencies())
        addModule(viewModelDependencies())
        addModule(networkDependencies(app.applicationContext))
        addModule(repositoryDependencies())
    }

    private fun addModule(activityModules: Kodein.Module) {
        kodein.addImport(activityModules, true)
    }
}

