package com.bugmakers.jarvistime.presentation.pages.authentication

import android.view.WindowManager
import androidx.fragment.app.commit
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityAuthorizationBinding
import com.bugmakers.jarvistime.presentation.base.BaseActivity
import com.bugmakers.jarvistime.presentation.extensions.closeWithTransition
import com.bugmakers.jarvistime.presentation.extensions.kodeinViewModel
import com.bugmakers.jarvistime.presentation.extensions.makeToolbarAsActionBar
import com.bugmakers.jarvistime.presentation.pages.authentication.OnAuthorizationChangeListener.AuthorizationFragmentType
import com.bugmakers.jarvistime.presentation.pages.authentication.login.LoginFragment
import com.bugmakers.jarvistime.presentation.pages.authentication.register.RegisterFragment
import com.bugmakers.jarvistime.presentation.entity.enums.AnimationType
import com.bugmakers.jarvistime.presentation.utils.getAnimResByType

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

        onAuthorizationFragmentChanged(AuthorizationFragmentType.LOGIN)
    }

    override fun onAuthorizationFragmentChanged(changeTo: AuthorizationFragmentType) {
        supportFragmentManager.commit {
            val (inAnim, outAnim) = getAnimResByType(AnimationType.SLIDE_LEFT)
            if (supportFragmentManager.fragments.size > 0) {
                setCustomAnimations(inAnim, outAnim)
            }

            val fragment = when (changeTo) {
                AuthorizationFragmentType.LOGIN -> LoginFragment()
                AuthorizationFragmentType.REGISTER -> RegisterFragment()
            }

            replace(R.id.fragmentContainer, fragment)
        }
    }
}