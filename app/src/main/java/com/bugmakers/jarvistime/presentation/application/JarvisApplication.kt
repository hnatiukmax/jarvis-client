package com.bugmakers.jarvistime.presentation.application

import android.app.Application
import com.bugmakers.jarvistime.presentation.di.ServiceLocator
import com.chibatching.kotpref.Kotpref
import es.dmoral.toasty.Toasty
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

        Kotpref.init(applicationContext)

        realmInit()
        configToasty()
    }

    private fun configToasty() {
        Toasty.Config.getInstance()
            .setTextSize(14)
            .apply()
    }

    private fun realmInit() {
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
    }
}