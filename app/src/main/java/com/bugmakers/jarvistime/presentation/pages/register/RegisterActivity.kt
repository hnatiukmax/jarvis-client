package com.bugmakers.jarvistime.presentation.pages.register

import androidx.lifecycle.Observer
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityRegisterBinding
import com.bugmakers.jarvistime.presentation.base.BaseActivity
import com.bugmakers.jarvistime.presentation.extensions.goTo
import com.bugmakers.jarvistime.presentation.extensions.makeToolbarAsActionBar
import com.bugmakers.jarvistime.presentation.pages.login.LogInActivity
import com.bugmakers.jarvistime.presentation.pages.main.MainActivity
import org.kodein.di.generic.instance

internal class RegisterActivity :
    BaseActivity<ActivityRegisterBinding, RegisterActivityViewModel>() {

    override val layoutId = R.layout.activity_register
    override val viewModel by instance<RegisterActivityViewModel>()

    override fun ActivityRegisterBinding.initView() {
        viewModel = this@RegisterActivity.viewModel
        makeToolbarAsActionBar(toolbar)
        toolbar.setLeftActionAsBackButton(true)

        haveAnAccount.setOnClickListener {
            goTo(LogInActivity::class.java)
            finish()
        }
    }

    override fun RegisterActivityViewModel.observeViewModel() {
        onRegister.observe(this@RegisterActivity, Observer {
            goTo(MainActivity::class.java)
            finish()
        })
    }
}
