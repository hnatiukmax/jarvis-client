package com.bugmakers.jarvistime.data.datasource

import com.bugmakers.jarvistime.domain.datasource.TaskDataSource
import com.bugmakers.jarvistime.domain.entity.Task

internal class LocalTaskDataSource : TaskDataSource<Task> {

    override fun <R> getTasks(): R {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createTask(body: Task): Nothing {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateTask(taskId: Int, body: Task): Nothing {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteTask(taskId: Int): Nothing {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}