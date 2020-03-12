package com.bugmakers.jarvistime.domain.entity

enum class TypeTask(val id: Int) {
    DO_FIRST(1),
    DO_LATER(2),
    DO_BY_OTHERS(3),
    DO_NOT_DO(4);

    fun getStringResId() = when (this) {
        DO_FIRST -> "DO_FIRST"
        DO_LATER -> "DO_LATER"
        DO_BY_OTHERS -> "DO_BY_OTHERS"
        DO_NOT_DO -> "DO_NOT_DO"
    }

}