package com.bugmakers.jarvistime.presentation.pages.splashscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bugmakers.jarvistime.presentation.extensions.goTo
import com.bugmakers.jarvistime.presentation.pages.introduction.IntroductionActivity
import com.bugmakers.jarvistime.presentation.pages.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goTo(MainActivity::class.java)
        finish()
    }
}
