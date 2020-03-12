package com.bugmakers.jarvistime.domain.entity

import com.bugmakers.jarvistime.R

enum class TaskType {
    DO_FIRST,
    DO_LATER,
    DO_BY_OTHERS,
    DO_NOT_DO,
    UNKNOW;

    val stringResId
        get() = when (this) {
            UNKNOW -> 0
            DO_FIRST -> R.string.type_task_do_first
            DO_LATER -> R.string.type_task_do_later
            DO_BY_OTHERS -> R.string.type_task_do_by_others
            DO_NOT_DO -> R.string.type_task_do_not_do
        }

    val intValue
        get() = when (this) {
            UNKNOW -> 0
            DO_FIRST -> 1
            DO_LATER -> 2
            DO_BY_OTHERS -> 3
            DO_NOT_DO -> 4
        }

    companion object {
        fun typeById(id: Int) =
            when (id) {
                1 -> DO_FIRST
                2 -> DO_LATER
                3 -> DO_BY_OTHERS
                4 -> DO_NOT_DO
                else -> UNKNOW
            }
    }
}