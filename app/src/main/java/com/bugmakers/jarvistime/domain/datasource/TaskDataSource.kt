package com.bugmakers.jarvistime.domain.datasource

internal interface TaskDataSource<T> {

    fun <R> getTasks() : R

    fun createTask(body : T)

    fun updateTask(taskId : Int, body : T)

    fun deleteTask(taskId : Int)
}