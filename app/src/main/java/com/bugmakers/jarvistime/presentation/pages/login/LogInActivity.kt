package com.bugmakers.jarvistime.presentation.pages.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityLogInBinding
import com.bugmakers.jarvistime.presentation.extensions.goTo
import com.bugmakers.jarvistime.presentation.extensions.makeToolbarAsActionBar
import com.bugmakers.jarvistime.presentation.pages.register.RegisterActivity

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private val viewModel = LogInActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        binding.apply {
            lifecycleOwner = this@LogInActivity
            viewModel = this@LogInActivity.viewModel

            init()
        }

        viewModel.observeViewModel()
    }

    private fun ActivityLogInBinding.init() {
        makeToolbarAsActionBar(toolbar)
        toolbar.setLeftActionAsBackButton(true)

        noAccount.setOnClickListener {
            goTo(RegisterActivity::class.java)
        }
    }

    private fun LogInActivityViewModel.observeViewModel() {

    }
}
