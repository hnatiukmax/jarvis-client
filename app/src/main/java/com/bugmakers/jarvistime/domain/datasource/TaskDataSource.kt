package com.bugmakers.jarvistime.domain.datasource

internal interface TaskDataSource<T> {

    fun <R> getTasks() : R

    fun createTask(body : T) : Nothing

    fun updatetask(taskId : Int, body : T) : Nothing

    fun deleteTask(taskId : Int) : Nothing
}