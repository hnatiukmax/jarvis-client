package com.bugmakers.jarvistime.presentation.pages.mainboard

import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.FragmentMainBoardBinding
import com.bugmakers.jarvistime.presentation.base.BaseFragment
import org.kodein.di.generic.instance

internal class MainBoardFragment : BaseFragment<FragmentMainBoardBinding, MainBoardFragmentViewModel>() {

    override val layoutId = R.layout.fragment_main_board
    override val viewModel by instance<MainBoardFragmentViewModel>()

    override fun FragmentMainBoardBinding.initView() {
        viewModel = this@MainBoardFragment.viewModel
    }

    override fun MainBoardFragmentViewModel.observeViewModel() {

    }
}