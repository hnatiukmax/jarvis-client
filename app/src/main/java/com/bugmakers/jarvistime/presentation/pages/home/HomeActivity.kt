package com.bugmakers.jarvistime.presentation.pages.home

import android.content.Context
import android.content.Intent
import androidx.fragment.app.commit
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ActivityHomeBinding
import com.bugmakers.jarvistime.presentation.base.BaseActivity
import com.bugmakers.jarvistime.presentation.extensions.kodeinViewModel
import com.bugmakers.jarvistime.presentation.pages.home.inbox.InboxFragment

internal class HomeActivity : BaseActivity<ActivityHomeBinding, HomeActivityViewModel>() {

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
    }

    override fun HomeActivityViewModel.observeViewModel() {

    }
}