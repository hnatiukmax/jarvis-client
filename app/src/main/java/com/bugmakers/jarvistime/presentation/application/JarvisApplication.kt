package com.bugmakers.jarvistime.presentation.application

import android.app.Application
import com.bugmakers.jarvistime.presentation.di.ServiceLocator
import io.realm.Realm
import io.realm.RealmConfiguration
import org.kodein.di.KodeinAware
import org.kodein.di.conf.ConfigurableKodein

class JarvisApplication : Application(), KodeinAware {

    override val kodein = ConfigurableKodein(mutable = true)

    private lateinit var serviceLocator: ServiceLocator

    override fun onCreate() {
        super.onCreate()
        serviceLocator = ServiceLocator(kodein, this)
        serviceLocator.setInjection()
        realmInit()
    }

    fun realmInit() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .build()
        Realm.setDefaultConfiguration(config)
    }
}