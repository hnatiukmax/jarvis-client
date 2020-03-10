package com.bugmakers.jarvistime.presentation.pages.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bugmakers.jarvistime.presentation.extensions.goTo
import com.bugmakers.jarvistime.presentation.pages.login.LogInActivity
import com.bugmakers.jarvistime.presentation.pages.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goTo(MainActivity::class.java)
        finish()
    }
}
