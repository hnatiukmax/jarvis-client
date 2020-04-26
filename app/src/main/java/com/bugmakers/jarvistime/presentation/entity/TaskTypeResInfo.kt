package com.bugmakers.jarvistime.presentation.entity

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

internal data class TaskTypeResInfo(
    @ColorRes val primaryColor: Int,
    @ColorRes val primaryColorLight: Int,
    @DrawableRes var iconRes: Int = 0,
    @StringRes var titleRes: Int = 0,
    @StringRes var descriptionRes: Int = 0
)