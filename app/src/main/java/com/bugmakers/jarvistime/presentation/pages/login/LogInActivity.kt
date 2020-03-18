package com.bugmakers.jarvistime.presentation.pages.login

import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityLogInBinding
import com.bugmakers.jarvistime.presentation.base.BaseActivity
import com.bugmakers.jarvistime.presentation.extensions.goTo
import com.bugmakers.jarvistime.presentation.extensions.makeToolbarAsActionBar
import com.bugmakers.jarvistime.presentation.pages.main.MainActivity
import com.bugmakers.jarvistime.presentation.pages.register.RegisterActivity
import org.kodein.di.generic.instance

internal class LogInActivity : BaseActivity<ActivityLogInBinding, LogInActivityViewModel>() {

    override val layoutId = R.layout.activity_log_in
    override val viewModel by instance<LogInActivityViewModel>()

    override fun ActivityLogInBinding.initView() {
        viewModel = this@LogInActivity.viewModel
        makeToolbarAsActionBar(toolbar)
        toolbar.setLeftActionAsBackButton(true)

        noAccount.setOnClickListener {
            goTo(RegisterActivity::class.java)
        }
    }

    override fun LogInActivityViewModel.observeViewModel() {
        onLogin.observe {
            goTo(MainActivity::class.java)
            finish()
        }
        isProgressVisible.observe { binding.logIn.isEnabled = !it }
    }
}
