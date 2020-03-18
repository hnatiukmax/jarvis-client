package com.bugmakers.jarvistime.presentation.pages.introduction

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.data.auth.getFacebookCallback
import com.bugmakers.jarvistime.databinding.ActivityIntroductionBinding
import com.bugmakers.jarvistime.domain.entity.AuthenticationType.*
import com.bugmakers.jarvistime.presentation.base.BaseActivity
import com.bugmakers.jarvistime.presentation.extensions.goTo
import com.bugmakers.jarvistime.presentation.extensions.showToast
import com.bugmakers.jarvistime.presentation.pages.login.LogInActivity
import com.bugmakers.jarvistime.presentation.pages.main.MainActivity
import com.bugmakers.jarvistime.presentation.view.introductionslider.IntroductionSliderAdapter
import com.bugmakers.jarvistime.presentation.view.introductionslider.IntroductionSliderItem
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookAuthorizationException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import org.kodein.di.generic.instance

internal class IntroductionActivity :
    BaseActivity<ActivityIntroductionBinding, IntroductionActivityViewModel>() {

    override val layoutId = R.layout.activity_introduction
    override val viewModel by instance<IntroductionActivityViewModel>()

    companion object {
        private const val REQUEST_CODE_GOOGLE_SIGN_IN = 1101
    }

    override fun ActivityIntroductionBinding.initView() {
        viewModel = this@IntroductionActivity.viewModel

        continueWithEmail.setOnClickListener {
            goTo(LogInActivity::class.java)
        }

//        initIntroductionSlider()
    }

    override fun IntroductionActivityViewModel.observeViewModel() {
        onAuth.observe {
            when (it) {
                GOOGLE -> startActivityForResult(viewModel.authGoogleIntent, REQUEST_CODE_GOOGLE_SIGN_IN)
                FACEBOOK -> LoginManager.getInstance().logInWithReadPermissions(this@IntroductionActivity, listOf("public_profile"))
                else -> goTo(LogInActivity::class.java)
            }
        }
        onRegisterCompleted.observe {
            goTo(MainActivity::class.java)
            finish()
        }
    }

    private fun initIntroductionSlider() {
        val introductionSliderItems = getIntroductionResources() ?: return

        val itemCount = introductionSliderItems.size

        val introductionSliderAdapter = IntroductionSliderAdapter(introductionSliderItems)
        val onIntroductionPageChangeCallback = OnIntroductionPageChangeCallback(itemCount)

        binding.apply {
            viewPager.let {
                it.adapter = introductionSliderAdapter
                it.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                it.offscreenPageLimit = 2
                it.setCurrentItem((introductionSliderAdapter.itemCount / 2) + 1, false)
                it.registerOnPageChangeCallback(onIntroductionPageChangeCallback)
            }
            pageIndicator.count = itemCount
        }
    }

    /**
     * This method return list of viewPager item.
     */
    @SuppressLint("Recycle")
    private fun getIntroductionResources(): List<IntroductionSliderItem>? {
        val itemLogoIds = resources.obtainTypedArray(R.array.introduction_item_logo_ids)
        val firstLabelIds = resources.obtainTypedArray(R.array.introduction_first_label_ids)
        val secondLabelIds = resources.obtainTypedArray(R.array.introduction_second_label_ids)

        if (itemLogoIds.length() != firstLabelIds.length() ||
            itemLogoIds.length() != secondLabelIds.length() ||
            firstLabelIds.length() != secondLabelIds.length()
        ) {
            return null
        }

        return List(itemLogoIds.length()) {
            IntroductionSliderItem(
                itemLogoResId = itemLogoIds.getResourceId(it, 0),
                introductionLabelFirstId = firstLabelIds.getResourceId(it, 0),
                introductionLabelSecondId = secondLabelIds.getResourceId(it, 0)
            )
        }
    }

    /**
     * Control state of pageIndicator and introductionBottomBackground.
     */
    private inner class OnIntroductionPageChangeCallback(
        val itemCount: Int
    ) : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding.pageIndicator.selection = position % itemCount
        }
    }

    /**
     * Handle result of social auth attempt execution.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.let {
            if (requestCode == REQUEST_CODE_GOOGLE_SIGN_IN && resultCode == Activity.RESULT_OK) {
                it.onGoogleAuthResult(data)
            } else {
                it.onFacebookAuthResult(requestCode, resultCode, data)
            }
        }
    }
}
