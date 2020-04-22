package com.bugmakers.jarvistime.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.domain.entity.TaskType
import com.bugmakers.jarvistime.presentation.entity.TaskTypeResInfo
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

@SuppressLint("Recycle")
internal fun getTaskResInfoByType(context: Context, taskType: TaskType) : TaskTypeResInfo {
    val iconIds = context.resources.obtainTypedArray(R.array.task_type_icon)
    val backgroundIds = context.resources.obtainTypedArray(R.array.task_type_background)
    val titleIds = context.resources.obtainTypedArray(R.array.task_type_title)

    return TaskTypeResInfo(
        backgroundRes = backgroundIds.getResourceId(taskType.ordinal, 0),
        iconRes = iconIds.getResourceId(taskType.ordinal, 0),
        titleRes = titleIds.getResourceId(taskType.ordinal, 0),
        description = titleIds.getResourceId(taskType.ordinal, 0)
    )
}