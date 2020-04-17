package com.bugmakers.jarvistime.presentation.di.modules

import androidx.lifecycle.ViewModelProvider
import com.bugmakers.jarvistime.presentation.extensions.bindViewModel
import com.bugmakers.jarvistime.presentation.pages.authentication.AuthenticationActivityViewModel
import com.bugmakers.jarvistime.presentation.pages.authentication.login.LoginFragmentViewModel
import com.bugmakers.jarvistime.presentation.pages.authentication.register.RegisterFragmentViewModel
import com.bugmakers.jarvistime.presentation.pages.introduction.IntroductionActivityViewModel
import com.bugmakers.jarvistime.presentation.pages.main.HomeActivityViewModel
import com.bugmakers.jarvistime.presentation.pages.mainboard.MainBoardFragmentViewModel
import com.bugmakers.jarvistime.presentation.pages.splashscreen.SplashScreenViewModel
import com.bugmakers.jarvistime.presentation.utils.base.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

internal fun viewModelDependencies() = Kodein.Module("viewModelDependencies") {
    bind<ViewModelProvider.Factory>() with singleton {
        ViewModelFactory(kodein.direct)
    }

    bindViewModel<SplashScreenViewModel>() with provider { SplashScreenViewModel() }
    bindViewModel<IntroductionActivityViewModel>() with provider {
        IntroductionActivityViewModel(instance(), instance())
    }
    bindViewModel<AuthenticationActivityViewModel>() with provider { AuthenticationActivityViewModel() }
    bindViewModel<LoginFragmentViewModel>() with provider { LoginFragmentViewModel(instance()) }
    bindViewModel<RegisterFragmentViewModel>() with provider { RegisterFragmentViewModel(instance()) }
    bindViewModel<HomeActivityViewModel>() with provider { HomeActivityViewModel() }
    bindViewModel<MainBoardFragmentViewModel>() with provider { MainBoardFragmentViewModel() }
}