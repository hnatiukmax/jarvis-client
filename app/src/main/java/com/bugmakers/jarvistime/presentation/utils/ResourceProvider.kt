package com.bugmakers.jarvistime.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.domain.entity.TaskType
import com.bugmakers.jarvistime.presentation.entity.TaskTypeResInfo
import com.bugmakers.jarvistime.presentation.entity.enums.AnimationType
import com.bugmakers.jarvistime.presentation.entity.enums.AnimationType.*
import com.bugmakers.jarvistime.presentation.entity.enums.NavigationPage
import com.bugmakers.jarvistime.presentation.entity.enums.TypeUIMessage

internal fun provideAnimResByType(animationType: AnimationType) =
    when (animationType) {
        SLIDE_DOWN -> R.anim.slide_down_enter to R.anim.slide_down_exit
        SLIDE_UP -> R.anim.slide_up_enter to R.anim.slide_up_exit
        SLIDE_LEFT -> R.anim.slide_left_enter to R.anim.slide_left_exit
        SLIDE_RIGHT -> R.anim.slide_in_left to R.anim.slide_out_right
        FADE -> R.anim.fade_exit to R.anim.fade_enter
    }

internal fun provideIconResByTypeMessage(typeMessage: TypeUIMessage) =
    when (typeMessage) {
        TypeUIMessage.ERROR -> R.drawable.ic_error
        TypeUIMessage.WARNING -> R.drawable.ic_warning
        TypeUIMessage.INFORM -> R.drawable.ic_info
    }

@SuppressLint("Recycle")
internal fun provideTaskResInfoByType(context: Context, taskType: TaskType): TaskTypeResInfo {
    val primaryColorIds = context.resources.obtainTypedArray(R.array.task_type_primary)
    val primaryColorLightIds = context.resources.obtainTypedArray(R.array.task_type_primary_light)
    val iconIds = context.resources.obtainTypedArray(R.array.task_type_icon)
    val titleIds = context.resources.obtainTypedArray(R.array.task_type_title)
    val descriptionIds = context.resources.obtainTypedArray(R.array.task_type_description)

    val index = taskType.ordinal

    return TaskTypeResInfo(
        primaryColor = primaryColorIds.getResourceId(index, 0),
        primaryColorLight = primaryColorLightIds.getResourceId(index, 0),
        iconRes = iconIds.getResourceId(index, 0),
        titleRes = titleIds.getResourceId(index, 0),
        descriptionRes = descriptionIds.getResourceId(index, 0)
    )
}

internal fun provideTitleResByNavigationPage(page: NavigationPage) =
    when (page) {
        NavigationPage.INBOX -> R.string.home_title_inbox
        NavigationPage.SETTINGS -> R.string.home_title_settings
        NavigationPage.PROFILE -> R.string.home_title_profile
    }