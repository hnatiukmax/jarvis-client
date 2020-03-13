package com.bugmakers.jarvistime.presentation.base

import androidx.appcompat.app.AppCompatActivity
import com.bugmakers.jarvistime.presentation.application.JarvisApplication
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

abstract class BaseActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by lazy {
        (application as JarvisApplication).kodein
    }
}