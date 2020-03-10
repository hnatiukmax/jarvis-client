package com.bugmakers.jarvistime.presentation.pages.introduction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityIntroductionBinding

class IntroductionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroductionBinding
    private val viewModel = IntroductionActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_introduction)
        binding.apply {
            lifecycleOwner = this@IntroductionActivity
            viewModel = this@IntroductionActivity.viewModel
        }

        viewModel.apply {

        }
    }
}
