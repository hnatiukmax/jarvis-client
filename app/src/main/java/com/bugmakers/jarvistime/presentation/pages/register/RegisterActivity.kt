package com.bugmakers.jarvistime.presentation.pages.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityRegisterBinding
import com.bugmakers.jarvistime.presentation.extensions.goTo
import com.bugmakers.jarvistime.presentation.extensions.makeToolbarAsActionBar
import com.bugmakers.jarvistime.presentation.pages.login.LogInActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel = RegisterActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.apply {
            lifecycleOwner = this@RegisterActivity
            viewModel = this@RegisterActivity.viewModel

            init()
        }

        viewModel.observeViewModel()
    }

    private fun ActivityRegisterBinding.init() {
        makeToolbarAsActionBar(toolbar)
        toolbar.setLeftActionAsBackButton(true)

        haveAnAccount.setOnClickListener {
            goTo(LogInActivity::class.java)
            finish()
        }
    }

    private fun RegisterActivityViewModel.observeViewModel() {

    }
}
