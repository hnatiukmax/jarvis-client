package com.bugmakers.jarvistime.presentation.pages.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityLogInBinding
import com.bugmakers.jarvistime.presentation.application.JarvisApplication
import com.bugmakers.jarvistime.presentation.extensions.goTo
import com.bugmakers.jarvistime.presentation.extensions.makeToolbarAsActionBar
import com.bugmakers.jarvistime.presentation.pages.main.MainActivity
import com.bugmakers.jarvistime.presentation.pages.register.RegisterActivity
import es.dmoral.toasty.Toasty
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class LogInActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by lazy {
        (application as JarvisApplication).kodein
    }

    private val viewModel by instance<LogInActivityViewModel>()

    private lateinit var binding: ActivityLogInBinding

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
        onLogin.observe(this@LogInActivity, Observer {
            goTo(MainActivity::class.java)
            finish()
        })

        onOpenMessageDialog.observe(this@LogInActivity, Observer {
            if (it.second) {
                Toasty.error(this@LogInActivity, it.first).show()
            } else {
                Toasty.info(this@LogInActivity, it.first).show()
            }
        })
    }
}
