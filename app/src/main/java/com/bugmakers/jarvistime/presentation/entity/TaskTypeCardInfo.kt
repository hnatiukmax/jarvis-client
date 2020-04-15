package com.bugmakers.jarvistime.presentation.entity

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

internal data class TaskTypeCardInfo(
    val taskTypeInfo : String,
    @DrawableRes @ColorRes val backgroundRes: Int,
    @DrawableRes var iconRes: Int = 0,
    @StringRes var titleRes: Int = 0,
    @StringRes var description: Int = 0
)