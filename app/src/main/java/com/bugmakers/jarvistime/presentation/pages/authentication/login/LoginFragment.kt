package com.bugmakers.jarvistime.presentation.pages.authentication.login

import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.FragmentLoginBinding
import com.bugmakers.jarvistime.presentation.base.BaseFragment
import com.bugmakers.jarvistime.presentation.extensions.goTo
import com.bugmakers.jarvistime.presentation.extensions.kodeinViewModel
import com.bugmakers.jarvistime.presentation.pages.authentication.OnAuthorizationChangeListener
import com.bugmakers.jarvistime.presentation.pages.main.HomeActivity
import com.bugmakers.jarvistime.presentation.entity.enums.AnimationType
import com.bugmakers.jarvistime.presentation.entity.enums.AuthorizationFragmentType.*

internal class LoginFragment : BaseFragment<FragmentLoginBinding, LoginFragmentViewModel>() {

    override val layoutId = R.layout.fragment_login
    override val viewModel: LoginFragmentViewModel by kodeinViewModel()

    override fun FragmentLoginBinding.initView() {
        viewModel = this@LoginFragment.viewModel

        noAccount.setOnClickListener {
            (activity as? OnAuthorizationChangeListener)?.onAuthorizationFragmentChanged(REGISTER)
        }
    }

    override fun LoginFragmentViewModel.observeViewModel() {
        onLogin.observe {
            goTo(HomeActivity::class.java, animationType = AnimationType.SLIDE_LEFT, close = true)
        }
    }
}