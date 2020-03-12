package com.bugmakers.jarvistime.presentation.pages.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityRegisterBinding
import com.bugmakers.jarvistime.presentation.application.JarvisApplication
import com.bugmakers.jarvistime.presentation.extensions.goTo
import com.bugmakers.jarvistime.presentation.extensions.makeToolbarAsActionBar
import com.bugmakers.jarvistime.presentation.pages.login.LogInActivity
import com.bugmakers.jarvistime.presentation.pages.main.MainActivity
import es.dmoral.toasty.Toasty
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class RegisterActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by lazy {
        (application as JarvisApplication).kodein
    }

    private val viewModel by instance<RegisterActivityViewModel>()

    private lateinit var binding: ActivityRegisterBinding

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
        onRegister.observe(this@RegisterActivity, Observer {
            goTo(MainActivity::class.java)
            finish()
        })

        onOpenMessageDialog.observe(this@RegisterActivity, Observer {
            if (it.second) {
                Toasty.warning(this@RegisterActivity, it.first).show()
            } else {
                Toasty.info(this@RegisterActivity, it.first).show()
            }
        })
    }
}
