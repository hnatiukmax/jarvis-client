package com.bugmakers.jarvistime.presentation.pages.mainboard

import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.FragmentInboxBinding
import com.bugmakers.jarvistime.presentation.base.BaseFragment
import org.kodein.di.generic.instance

internal class MainBoardFragment : BaseFragment<FragmentInboxBinding, MainBoardFragmentViewModel>() {

    override val layoutId = R.layout.fragment_inbox
    override val viewModel by instance<MainBoardFragmentViewModel>()

    override fun FragmentInboxBinding.initView() {
        viewModel = this@MainBoardFragment.viewModel
    }

    override fun MainBoardFragmentViewModel.observeViewModel() {

    }
}