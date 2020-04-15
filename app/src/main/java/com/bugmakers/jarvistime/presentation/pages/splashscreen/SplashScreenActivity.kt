package com.bugmakers.jarvistime.presentation.pages.splashscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bugmakers.jarvistime.presentation.application.JarvisApplication
import com.bugmakers.jarvistime.presentation.extensions.goTo
import com.bugmakers.jarvistime.presentation.extensions.kodeinViewModel
import com.bugmakers.jarvistime.presentation.pages.introduction.IntroductionActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class SplashScreenActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by lazy {
        (application as JarvisApplication).kodein
    }

    private val viewModel : SplashScreenViewModel by kodeinViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goTo(IntroductionActivity::class.java)
        finish()
    }
}
