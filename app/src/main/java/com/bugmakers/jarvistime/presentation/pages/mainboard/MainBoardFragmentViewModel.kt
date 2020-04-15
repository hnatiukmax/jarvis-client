package com.bugmakers.jarvistime.presentation.pages.mainboard

import com.bugmakers.jarvistime.presentation.base.BaseViewModel
import com.bugmakers.jarvistime.presentation.extensions.enable

internal class MainBoardFragmentViewModel : BaseViewModel() {

    fun onClick() {
        isProgressVisible.enable()
    }

}