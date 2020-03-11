package com.bugmakers.jarvistime.domain.entity

enum class TypeTask {
    DO_FIRST,
    DO_LATER,
    DO_BY_OTHERS,
    DO_NOT_DO;

    fun getStringResId() = when (this) {
        DO_FIRST -> "DO_FIRST"
        DO_LATER -> "DO_LATER"
        DO_BY_OTHERS -> "DO_BY_OTHERS"
        DO_NOT_DO -> "DO_NOT_DO"
    }

}