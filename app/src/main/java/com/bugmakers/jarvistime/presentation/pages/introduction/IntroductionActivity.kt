package com.bugmakers.jarvistime.presentation.pages.introduction

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityIntroductionBinding
import com.bugmakers.jarvistime.domain.entity.AuthorizationType.FACEBOOK
import com.bugmakers.jarvistime.domain.entity.AuthorizationType.GOOGLE
import com.bugmakers.jarvistime.presentation.base.BaseActivity
import com.bugmakers.jarvistime.presentation.extensions.goTo
import com.bugmakers.jarvistime.presentation.extensions.kodeinViewModel
import com.bugmakers.jarvistime.presentation.pages.authentication.AuthorizationActivity
import com.bugmakers.jarvistime.presentation.pages.main.HomeActivity
import com.bugmakers.jarvistime.presentation.entity.enums.AnimationType
import com.bugmakers.jarvistime.presentation.utils.ViewPagerTransformer
import com.bugmakers.jarvistime.presentation.utils.listeners.getOnPageChangedCallback
import com.bugmakers.jarvistime.presentation.view.introductionslider.IntroductionSliderAdapter
import com.bugmakers.jarvistime.presentation.view.introductionslider.IntroductionSliderItem
import com.facebook.login.LoginManager
import miaoyongjun.pagetransformer.TransitionEffect

internal class IntroductionActivity : BaseActivity<ActivityIntroductionBinding, IntroductionActivityViewModel>() {

    override val layoutId = R.layout.activity_introduction
    override val viewModel : IntroductionActivityViewModel by kodeinViewModel()

    companion object {
        private const val REQUEST_CODE_GOOGLE_SIGN_IN = 1101
    }

    override fun ActivityIntroductionBinding.initView() {
        viewModel = this@IntroductionActivity.viewModel

        initIntroductionSlider()
    }

    override fun IntroductionActivityViewModel.observeViewModel() {
        onAuth.observe {
            when (it) {
                GOOGLE -> startActivityForResult(viewModel.authGoogleIntent, REQUEST_CODE_GOOGLE_SIGN_IN)
                FACEBOOK -> LoginManager.getInstance().logInWithReadPermissions(this@IntroductionActivity, listOf("public_profile"))
                else -> goTo(AuthorizationActivity::class.java, AnimationType.SLIDE_LEFT)
            }
        }
        onRegisterCompleted.observe {
            goTo(HomeActivity::class.java, AnimationType.FADE)
        }
    }

    private fun initIntroductionSlider() {
        val introductionSliderItems = getIntroductionResources() ?: return

        val itemCount = introductionSliderItems.size

        val introductionSliderAdapter = IntroductionSliderAdapter(introductionSliderItems)
        val onIntroductionPageChangeCallback = getOnPageChangedCallback { position ->
            binding.pageIndicator.selectedItem = position % itemCount
        }

        val pageTransformer = ViewPagerTransformer(TransitionEffect.Fade)

        binding.apply {
            viewPager.let {
                it.adapter = introductionSliderAdapter
                it.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                it.offscreenPageLimit = 2
                it.setCurrentItem((introductionSliderAdapter.itemCount / 2) + 1, false)
                it.registerOnPageChangeCallback(onIntroductionPageChangeCallback)
                it.setPageTransformer(pageTransformer)
                pageIndicator.selectedItem = it.currentItem % itemCount
            }
        }
    }

    /**
     * This method return list of viewPager items data.
     */
    @SuppressLint("Recycle")
    private fun getIntroductionResources(): List<IntroductionSliderItem>? {
        val firstLabelIds = resources.obtainTypedArray(R.array.introduction_first_label_ids)
        val secondLabelIds = resources.obtainTypedArray(R.array.introduction_second_label_ids)
        val itemLogoIds = resources.obtainTypedArray(R.array.introduction_item_logo_ids)

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
     * Handle result of social auth attempt execution.
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
