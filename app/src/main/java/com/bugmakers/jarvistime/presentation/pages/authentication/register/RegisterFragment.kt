package com.bugmakers.jarvistime.presentation.pages.authentication.register

import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.FragmentRegisterBinding
import com.bugmakers.jarvistime.presentation.base.BaseFragment
import com.bugmakers.jarvistime.presentation.extensions.goTo
import com.bugmakers.jarvistime.presentation.extensions.kodeinViewModel
import com.bugmakers.jarvistime.presentation.pages.authentication.OnAuthorizationChangeListener
import com.bugmakers.jarvistime.presentation.pages.main.HomeActivity
import com.bugmakers.jarvistime.presentation.entity.enums.AnimationType
import com.bugmakers.jarvistime.presentation.entity.enums.AuthorizationFragmentType.*

internal class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterFragmentViewModel>() {

    override val layoutId = R.layout.fragment_register
    override val viewModel: RegisterFragmentViewModel by kodeinViewModel()

    override fun FragmentRegisterBinding.initView() {
        viewModel = this@RegisterFragment.viewModel

        haveAnAccount.setOnClickListener {
            (activity as? OnAuthorizationChangeListener)?.onAuthorizationFragmentChanged(LOGIN)
        }
    }

    override fun RegisterFragmentViewModel.observeViewModel() {
        onRegister.observe {
            goTo(HomeActivity::class.java, animationType = AnimationType.SLIDE_LEFT, close = true)
        }
    }
}