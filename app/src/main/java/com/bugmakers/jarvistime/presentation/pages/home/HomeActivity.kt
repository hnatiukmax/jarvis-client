package com.bugmakers.jarvistime.presentation.pages.home

import android.content.Context
import android.content.Intent
import androidx.fragment.app.commit
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityHomeBinding
import com.bugmakers.jarvistime.presentation.base.BaseActivity
import com.bugmakers.jarvistime.presentation.entity.enums.NavigationPage
import com.bugmakers.jarvistime.presentation.extensions.kodeinViewModel
import com.bugmakers.jarvistime.presentation.pages.home.inbox.InboxFragment
import com.bugmakers.jarvistime.presentation.utils.provideTitleResByNavigationPage
import com.bugmakers.jarvistime.presentation.view.BottomNavigationMenu

internal class HomeActivity : BaseActivity<ActivityHomeBinding, HomeActivityViewModel>(),
    BottomNavigationMenu.OnMenuItemClickListener {

    override val layoutId = R.layout.activity_home
    override val viewModel: HomeActivityViewModel by kodeinViewModel()

    companion object {
        fun getIntent(context: Context) = Intent(context, this::class.java)
    }

    override fun ActivityHomeBinding.initView() {
        viewModel = this@HomeActivity.viewModel

        supportFragmentManager.commit {
            add(R.id.container, InboxFragment.newInstance())
        }

        initBottomMenu()
    }

    override fun HomeActivityViewModel.observeViewModel() {

    }

    private fun initBottomMenu() {
        binding.navigationMenu.apply {
            onMenuItemClickListener = this@HomeActivity

            addMenuItems(
                R.drawable.ic_inbox to NavigationPage.INBOX,
                R.drawable.ic_profile to NavigationPage.PROFILE,
                R.drawable.ic_settings to NavigationPage.SETTINGS
            )
        }
    }

    /**
     * Implementation of OnMenuItemClickListener.
     * Handle page changing.
     * @param page
     */
    override fun onMenuItemClick(page: NavigationPage) {
        val titleRes = provideTitleResByNavigationPage(page)

        binding.mainToolbar.setTitleText(titleRes)
    }
}