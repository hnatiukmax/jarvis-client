package com.bugmakers.jarvistime.presentation.di

import android.app.Application
import com.bugmakers.jarvistime.BuildConfig
import com.bugmakers.jarvistime.data.network.client.AuthenticationInterceptor
import com.bugmakers.jarvistime.data.network.client.Authenticator
import com.bugmakers.jarvistime.data.network.client.RestClient
import com.bugmakers.jarvistime.data.network.client.authService
import com.bugmakers.jarvistime.data.network.mapper.*
import com.bugmakers.jarvistime.data.repository.AuthRepository
import com.bugmakers.jarvistime.data.service.AuthService
import com.bugmakers.jarvistime.presentation.pages.introduction.IntroductionActivityViewModel
import com.bugmakers.jarvistime.presentation.pages.login.LogInActivityViewModel
import com.bugmakers.jarvistime.presentation.pages.register.RegisterActivityViewModel
import com.bugmakers.jarvistime.presentation.pages.splashscreen.SplashScreenViewModel
import com.squareup.moshi.Moshi
import org.kodein.di.Kodein
import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.generic.*

class ServiceLocator(
    private val kodein: ConfigurableKodein,
    private val app: Application
) {

    enum class AppConstant {
        BASE_URL,
        DATABASE_PREFIX
    }

    fun setInjection() {
        kodein.clear()
        addModule(applicationIdentifiersDependencies())
        addModule(viewModelDependencies())
        addModule(networkDependencies())
        addModule(repositoryDependencies())
    }

    private fun applicationIdentifiersDependencies() = Kodein.Module("identifiersModule") {
        constant(AppConstant.BASE_URL) with BuildConfig.API_URL
        constant(AppConstant.DATABASE_PREFIX) with ""
    }

    private fun viewModelDependencies() = Kodein.Module("viewModelDependencies") {
        bind<SplashScreenViewModel>() with provider {
            SplashScreenViewModel()
        }
        bind<IntroductionActivityViewModel>() with provider {
            IntroductionActivityViewModel()
        }
        bind<LogInActivityViewModel>() with provider {
            LogInActivityViewModel(instance())
        }
        bind<RegisterActivityViewModel>() with provider {
            RegisterActivityViewModel(instance())
        }
    }

    private fun networkDependencies() = Kodein.Module("networkDependencies") {
        bind<AuthenticationInterceptor>() with singleton { AuthenticationInterceptor() }
        bind<Authenticator>() with singleton { instance<AuthenticationInterceptor>() }
        bind<Moshi>() with singleton { MoshiFactory().newInstance() }
        bind<Deserializer>() with singleton { MoshiDesirializer(instance()) }
        bind<Serializer>() with provider { MoshiSerializer(instance()) }
        bind<RestClient>() with singleton {
            RestClient(
                instance(AppConstant.BASE_URL),
                instance(),
                instance()
            )
        }

        bind<AuthService>() with singleton { instance<RestClient>().authService }
    }

    private fun repositoryDependencies() = Kodein.Module("repositoryDependencies") {
        bind<AuthRepository>() with provider { AuthRepository(instance()) }
    }

    private fun addModule(activityModules: Kodein.Module) {
        kodein.addImport(activityModules, true)
    }
}

