package com.bugmakers.jarvistime.presentation.utils

import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.presentation.entity.enums.AnimationType
import com.bugmakers.jarvistime.presentation.entity.enums.AnimationType.*
import com.bugmakers.jarvistime.presentation.entity.enums.TypeUIMessage

internal fun getAnimResByType(animationType: AnimationType) =
    when (animationType) {
        SLIDE_DOWN -> R.anim.slide_down_enter to R.anim.slide_down_exit
        SLIDE_UP -> R.anim.slide_up_enter to R.anim.slide_up_exit
        SLIDE_LEFT -> R.anim.slide_left_enter to R.anim.slide_left_exit
        SLIDE_RIGHT -> R.anim.slide_in_left to R.anim.slide_out_right
        FADE -> R.anim.fade_exit to R.anim.fade_enter
    }

internal fun getIconResByTypeMessage(typeMessage: TypeUIMessage) =
    when (typeMessage) {
        TypeUIMessage.ERROR -> R.drawable.ic_error
        TypeUIMessage.WARNING -> R.drawable.ic_warning
        TypeUIMessage.INFORM -> R.drawable.ic_info
    }