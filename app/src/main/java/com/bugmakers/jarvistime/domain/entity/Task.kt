package com.bugmakers.jarvistime.domain.entity

internal data class Task(
    val id : Int,
    val type: TaskType,
    val description : String = "",
    val isCompleted : Boolean
)