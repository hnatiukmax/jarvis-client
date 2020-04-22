package com.bugmakers.jarvistime.presentation.pages.authentication

import android.view.WindowManager
import androidx.fragment.app.commit
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityAuthorizationBinding
import com.bugmakers.jarvistime.presentation.base.BaseActivity
import com.bugmakers.jarvistime.presentation.entity.enums.AnimationType
import com.bugmakers.jarvistime.presentation.entity.enums.AuthorizationFragmentType.*
import com.bugmakers.jarvistime.presentation.entity.enums.AuthorizationFragmentType
import com.bugmakers.jarvistime.presentation.extensions.*
import com.bugmakers.jarvistime.presentation.extensions.closeWithTransition
import com.bugmakers.jarvistime.presentation.extensions.makeToolbarAsActionBar
import com.bugmakers.jarvistime.presentation.extensions.setCustomAnimations
import com.bugmakers.jarvistime.presentation.pages.authentication.login.LoginFragment
import com.bugmakers.jarvistime.presentation.pages.authentication.register.RegisterFragment

internal class AuthorizationActivity : BaseActivity<ActivityAuthorizationBinding, AuthenticationActivityViewModel>(),
        OnAuthorizationChangeListener {

    override val layoutId = R.layout.activity_authorization
    override val viewModel: AuthenticationActivityViewModel by kodeinViewModel()

    override fun ActivityAuthorizationBinding.initView() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        viewModel = this@AuthorizationActivity.viewModel

        toolbar.apply {
            makeToolbarAsActionBar(this)
            setLeftActionClickListener {
                closeWithTransition(AnimationType.SLIDE_RIGHT)
            }
        }

        onAuthorizationFragmentChanged(LOGIN)
    }

    override fun onAuthorizationFragmentChanged(changeTo: AuthorizationFragmentType) {
        supportFragmentManager.commit {
            val transition = when (changeTo) {
                LOGIN -> AnimationType.SLIDE_RIGHT
                REGISTER -> AnimationType.SLIDE_LEFT
            }

            if (supportFragmentManager.fragments.size > 0) {
                setCustomAnimations(transition)
            }

            val fragment = when (fragmentCount) {
                in 0..1 -> when (changeTo) {
                    LOGIN -> LoginFragment()
                    REGISTER -> RegisterFragment()
                }
                else -> null
            }

            when (supportFragmentManager.fragments.size) {
                0 -> add(R.id.fragmentContainer, fragment ?: return@commit)
                1 -> {
                    hide(getFragment(0) ?: return@commit)
                    add(R.id.fragmentContainer, fragment ?: return@commit)
                }
                else -> {
                    show(getFragment(0) ?: return@commit)
                    hide(getFragment(1) ?: return@commit)
                }
            }
        }
    }
}