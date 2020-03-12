package com.bugmakers.jarvistime.presentation.view.introductionslider

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class IntroductionSliderItem(
    @DrawableRes val itemLogoResId : Int,
    @StringRes val introductionLabelFirstId : Int,
    @StringRes val introductionLabelSecondId : Int
)